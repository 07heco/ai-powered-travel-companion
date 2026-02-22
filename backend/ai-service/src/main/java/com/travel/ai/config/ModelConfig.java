package com.travel.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "spring.ai.model")
public class ModelConfig {
    private TextGeneration textGeneration;
    private ImageRecognition imageRecognition;
    private Local local;

    @Data
    public static class TextGeneration {
        private String defaultModel;
        private List<String> options;
    }

    @Data
    public static class ImageRecognition {
        private String defaultModel;
        private List<String> options;
    }

    @Data
    public static class Local {
        private boolean enabled;
        private TextGenerationModel textGeneration;
        private ImageRecognitionModel imageRecognition;

        @Data
        public static class TextGenerationModel {
            private String modelPath;
        }

        @Data
        public static class ImageRecognitionModel {
            private String modelPath;
        }
    }
}