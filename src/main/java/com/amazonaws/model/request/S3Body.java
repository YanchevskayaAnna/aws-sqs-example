package com.amazonaws.model.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonAutoDetect
public class S3Body {
    private String s3SchemaVersion;
    private String configurationId;
    private S3Bucket bucket;
    private S3Object object;
}
