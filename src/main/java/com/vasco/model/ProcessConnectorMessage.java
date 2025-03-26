package com.vasco.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ProcessConnectorMessage {

   int processId;
   String processName;
   String versionName;
   String caseId;
   WorkFlowEngine nextWorkFlowEngine;
   @Autowired
   ArrayList<Variable> variables;
   @Autowired
   ArrayList<DataObject> dataObjects;

}
