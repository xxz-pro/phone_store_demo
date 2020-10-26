package com.southwind.phone_store_demo.service.impl;

import com.southwind.phone_store_demo.entity.PhoneCategory;
import com.southwind.phone_store_demo.entity.PhoneInfo;
import com.southwind.phone_store_demo.entity.PhoneSpecs;
import com.southwind.phone_store_demo.enums.ResultEnums;
import com.southwind.phone_store_demo.exception.PhoneException;
import com.southwind.phone_store_demo.repository.PhoneCategoryRepository;
import com.southwind.phone_store_demo.repository.PhoneInfoRepository;
import com.southwind.phone_store_demo.repository.PhoneSpecsRepository;
import com.southwind.phone_store_demo.service.PhoneService;
import com.southwind.phone_store_demo.util.PhoneUtil;
import com.southwind.phone_store_demo.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;
    @Autowired
    private PhoneInfoRepository phoneInfoRepository;
    @Autowired
    private PhoneSpecsRepository phoneSpecsRepository;

    @Override
    public DataVO findDataVO() {
        DataVO dataVO = new DataVO();

        //类型
        List<PhoneCategory> phoneCategoryList = phoneCategoryRepository.findAll();
        List<PhoneCategoryVO> phoneCategoryVOList = phoneCategoryList.stream()
                .map(e -> new PhoneCategoryVO(
                        e.getCategoryName(),
                        e.getCategoryType()
                )).collect(Collectors.toList());
        dataVO.setCategories(phoneCategoryVOList);

        //手机
        List<PhoneInfo> phoneInfoList = phoneInfoRepository.findAllByCategoryType(phoneCategoryList.get(0).getCategoryType());
        //常规
       /* List<PhoneInfoVO> phoneInfoVOList = new ArrayList<>();
        for (PhoneInfo phoneInfo : phoneInfoList) {
            PhoneInfoVO phoneInfoVO = new PhoneInfoVO();
            BeanUtils.copyProperties(phoneInfo,phoneInfoVO);
            phoneInfoVO.setTag(PhoneUtil.createTag(phoneInfo.getPhoneTag()));
            phoneInfoVOList.add(phoneInfoVO);
        }*/
        //stream
        List<PhoneInfoVO> phoneInfoVOList = phoneInfoList.stream()
                .map(e -> new PhoneInfoVO(
                        e.getPhoneId(),
                        e.getPhoneName(),
                        e.getPhonePrice() + ".00",
                        e.getPhoneDescription(),
                        PhoneUtil.createTag(e.getPhoneTag()),
                        e.getPhoneIcon()
                )).collect(Collectors.toList());
        dataVO.setPhones(phoneInfoVOList);
        return dataVO;
    }

    @Override
    public List<PhoneInfoVO> findPhoneInfoVOByCategoryType(Integer categoryType) {
        List<PhoneInfo> phoneInfoList = phoneInfoRepository.findAllByCategoryType(categoryType);
        List<PhoneInfoVO> phoneInfoVOList = phoneInfoList.stream()
                .map(e -> new PhoneInfoVO(
                        e.getPhoneId(),
                        e.getPhoneName(),
                        e.getPhonePrice() + ".00",
                        e.getPhoneDescription(),
                        PhoneUtil.createTag(e.getPhoneTag()),
                        e.getPhoneIcon()
                )).collect(Collectors.toList());
        return phoneInfoVOList;
    }

    @Override
    public SpecsPackageVO findSpecsByPhoneId(Integer phoneId) {
        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneId).get();
        List<PhoneSpecs> phoneSpecsList = phoneSpecsRepository.findAllByPhoneId(phoneId);
        List<PhoneSpecsVO> phoneSpecsVOList = new ArrayList<>();
        List<PhoneSpecsCasVO> phoneSpecsCasVOList = new ArrayList<>();
        for (PhoneSpecs phoneSpecs : phoneSpecsList) {
            PhoneSpecsVO phoneSpecsVO = new PhoneSpecsVO();
            PhoneSpecsCasVO phoneSpecsCasVO = new PhoneSpecsCasVO();
            BeanUtils.copyProperties(phoneSpecs, phoneSpecsVO);
            BeanUtils.copyProperties(phoneSpecs, phoneSpecsCasVO);
            phoneSpecsVOList.add(phoneSpecsVO);
            phoneSpecsCasVOList.add(phoneSpecsCasVO);
        }
        TreeVO treeVO = new TreeVO();
        treeVO.setV(phoneSpecsVOList);
        List<TreeVO> treeVOList = new ArrayList<>();
        if (!treeVO.getV().isEmpty()) {
            treeVOList.add(treeVO);
        }
        SkuVO skuVO = new SkuVO();
        Integer price = phoneInfo.getPhonePrice().intValue();
        skuVO.setPrice(price + ".00");
        skuVO.setStock_num(phoneInfo.getPhoneStock());
        skuVO.setTree(treeVOList);
        skuVO.setList(phoneSpecsCasVOList);
        SpecsPackageVO specsPackageVO = new SpecsPackageVO();
        specsPackageVO.setSku(skuVO);
        Map<String, String> goods = new HashMap<>();
        goods.put("picture", phoneInfo.getPhoneIcon());
        specsPackageVO.setGoods(goods);
        return specsPackageVO;
    }

    @Override
    public void subStock(Integer specsId, Integer quantity) {
        PhoneSpecs phoneSpecs = phoneSpecsRepository.findById(specsId).get();
        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();
        Integer result = phoneSpecs.getSpecsStock() - quantity;
        Integer phoneResult = phoneInfo.getPhoneStock() - quantity;
        if (result < 0 || phoneResult < 0) {
            log.error("【扣库存】库存不足");
            throw new PhoneException(ResultEnums.PHONE_STOCK_ERROR);
        }

        phoneSpecs.setSpecsStock(result);
        phoneSpecsRepository.save(phoneSpecs);
        phoneInfo.setPhoneStock(result);
        phoneInfoRepository.save(phoneInfo);
    }
}
