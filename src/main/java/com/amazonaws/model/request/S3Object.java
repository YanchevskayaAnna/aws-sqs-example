package com.amazonaws.model.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonAutoDetect
public class S3Object {

    private String key;
    private int size;
    private String eTag;
    private String sequencer;

}
