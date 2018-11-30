/*
 * 
 * Access to view and update game states
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.tcoffman.ttwb.service.api.data;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tcoffman.ttwb.service.api.data.OperationPatternResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * RuleResource
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class RuleResource {
  @JsonProperty("resource")
  private String resource = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("resultLabel")
  private String resultLabel = null;

  @JsonProperty("resultResource")
  private String resultResource = null;

  @JsonProperty("operationPatterns")
  private List<OperationPatternResource> operationPatterns = null;

  public RuleResource resource(String resource) {
    this.resource = resource;
    return this;
  }

   /**
   * Get resource
   * @return resource
  **/
  @ApiModelProperty(value = "")
  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public RuleResource label(String label) {
    this.label = label;
    return this;
  }

   /**
   * Get label
   * @return label
  **/
  @ApiModelProperty(value = "")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public RuleResource resultLabel(String resultLabel) {
    this.resultLabel = resultLabel;
    return this;
  }

   /**
   * Get resultLabel
   * @return resultLabel
  **/
  @ApiModelProperty(value = "")
  public String getResultLabel() {
    return resultLabel;
  }

  public void setResultLabel(String resultLabel) {
    this.resultLabel = resultLabel;
  }

  public RuleResource resultResource(String resultResource) {
    this.resultResource = resultResource;
    return this;
  }

   /**
   * Get resultResource
   * @return resultResource
  **/
  @ApiModelProperty(value = "")
  public String getResultResource() {
    return resultResource;
  }

  public void setResultResource(String resultResource) {
    this.resultResource = resultResource;
  }

  public RuleResource operationPatterns(List<OperationPatternResource> operationPatterns) {
    this.operationPatterns = operationPatterns;
    return this;
  }

  public RuleResource addOperationPatternsItem(OperationPatternResource operationPatternsItem) {
    if (this.operationPatterns == null) {
      this.operationPatterns = new ArrayList<>();
    }
    this.operationPatterns.add(operationPatternsItem);
    return this;
  }

   /**
   * Get operationPatterns
   * @return operationPatterns
  **/
  @ApiModelProperty(value = "")
  public List<OperationPatternResource> getOperationPatterns() {
    return operationPatterns;
  }

  public void setOperationPatterns(List<OperationPatternResource> operationPatterns) {
    this.operationPatterns = operationPatterns;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RuleResource ruleResource = (RuleResource) o;
    return Objects.equals(this.resource, ruleResource.resource) &&
        Objects.equals(this.label, ruleResource.label) &&
        Objects.equals(this.resultLabel, ruleResource.resultLabel) &&
        Objects.equals(this.resultResource, ruleResource.resultResource) &&
        Objects.equals(this.operationPatterns, ruleResource.operationPatterns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resource, label, resultLabel, resultResource, operationPatterns);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuleResource {\n");
    
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    resultLabel: ").append(toIndentedString(resultLabel)).append("\n");
    sb.append("    resultResource: ").append(toIndentedString(resultResource)).append("\n");
    sb.append("    operationPatterns: ").append(toIndentedString(operationPatterns)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

