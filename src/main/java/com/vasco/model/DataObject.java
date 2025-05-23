package com.vasco.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataObject {
   String id;
   String name;
   String type;
   String url;
   Metadata metadata;
}
