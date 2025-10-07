package com.vasco.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HandoverMessage {

   String processId;
   String processName;
   String versionName;
   String caseId;
   WorkflowEngine nextWorkflowEngine;
   Variables variables;
   List<DataObject> dataObjects;

}
