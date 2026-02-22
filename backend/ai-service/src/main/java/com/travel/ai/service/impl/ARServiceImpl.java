package com.travel.ai.service.impl;

import com.travel.ai.dao.ARLandmarkDao;
import com.travel.ai.entity.ARLandmark;
import com.travel.ai.service.ARService;
import com.travel.ai.service.ModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ARServiceImpl implements ARService {

    @Resource
    private ARLandmarkDao arLandmarkDao;

    @Resource
    private ModelService modelService;

    @Override
    public ARLandmark recognizeLandmark(String imageUrl) {
        // 使用AI模型识别图像
        String recognitionResult = modelService.recognizeImage(imageUrl);

        // 解析识别结果，这里简化处理，实际项目中应该根据API返回格式解析
        // 假设识别结果中包含景点名称
        String landmarkName = "故宫博物院"; // 默认值

        // 尝试从识别结果中提取景点名称
        if (recognitionResult.contains("故宫")) {
            landmarkName = "故宫博物院";
        } else if (recognitionResult.contains("长城")) {
            landmarkName = "长城";
        } else if (recognitionResult.contains("西湖")) {
            landmarkName = "西湖";
        }

        // 根据识别到的景点名称查询数据库
        List<ARLandmark> landmarks = arLandmarkDao.findAll();
        final String finalLandmarkName = landmarkName;
        List<ARLandmark> filteredLandmarks = landmarks.stream()
                .filter(landmark -> landmark.getName().equals(finalLandmarkName))
                .collect(Collectors.toList());

        if (!filteredLandmarks.isEmpty()) {
            return filteredLandmarks.get(0);
        }

        // 如果没有找到，创建一个模拟的结果
        ARLandmark mockLandmark = new ARLandmark();
        mockLandmark.setName(landmarkName);
        mockLandmark.setEnglishName("The Palace Museum");
        mockLandmark.setCategory("世界文化遗产");
        mockLandmark.setRating(4.9);
        mockLandmark.setDescription("故宫博物院建立于1925年，是在明清两代皇宫紫禁城的基础上建立起来的，是中国最大的古代文化艺术博物馆。");
        mockLandmark.setHistory("始建于1406年，距今已有600余年历史");
        mockLandmark.setOpenTime("08:30-17:00");
        mockLandmark.setTicketPrice("60元");
        mockLandmark.setHighlights("太和殿,养心殿,珍宝馆,钟表馆");
        mockLandmark.setTips("建议预留3-4小时游览时间,周一闭馆,需提前预约");
        mockLandmark.setLocation("北京市东城区景山前街4号");
        mockLandmark.setLatitude(39.916345);
        mockLandmark.setLongitude(116.397155);
        mockLandmark.setImageUrl(imageUrl);
        mockLandmark.setRecognitionKeywords("故宫,紫禁城,明清皇宫,北京");

        return mockLandmark;
    }

    @Override
    public ARLandmark getLandmarkById(Long id) {
        return arLandmarkDao.findById(id).orElse(null);
    }

    @Override
    public List<ARLandmark> getNearbyLandmarks(Double latitude, Double longitude, Integer radius) {
        // 这里应该实现基于地理距离的查询
        // 由于是示例，我们返回所有景点
        // 实际项目中应该使用空间索引或距离计算
        return arLandmarkDao.findAll();
    }

    @Override
    public List<ARLandmark> searchLandmarks(String keyword) {
        List<ARLandmark> landmarks = arLandmarkDao.findAll();
        return landmarks.stream()
                .filter(landmark -> landmark.getName().contains(keyword) || 
                        landmark.getDescription() != null && landmark.getDescription().contains(keyword) || 
                        landmark.getRecognitionKeywords() != null && landmark.getRecognitionKeywords().contains(keyword))
                .collect(Collectors.toList());
    }
}