package com.vasco.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CamundaDataObject implements Serializable {
   private static final long serialVersionUID = 1L;

   String id;
   String name;
   String type;
   String url;
   String metadataUploadedBy;
   String metadataUploadTime;
   String metadataSize;
}
