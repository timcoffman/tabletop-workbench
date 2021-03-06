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
import com.tcoffman.ttwb.service.api.data.RulesResource;
import com.tcoffman.ttwb.service.api.data.StagesResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * StageResource
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class StageResource {
  @JsonProperty("resource")
  private String resource = null;

  @JsonProperty("stages")
  private StagesResource stages = null;

  @JsonProperty("rules")
  private RulesResource rules = null;

  @JsonProperty("label")
  private String label = null;

  public StageResource resource(String resource) {
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

  public StageResource stages(StagesResource stages) {
    this.stages = stages;
    return this;
  }

   /**
   * Get stages
   * @return stages
  **/
  @ApiModelProperty(value = "")
  public StagesResource getStages() {
    return stages;
  }

  public void setStages(StagesResource stages) {
    this.stages = stages;
  }

  public StageResource rules(RulesResource rules) {
    this.rules = rules;
    return this;
  }

   /**
   * Get rules
   * @return rules
  **/
  @ApiModelProperty(value = "")
  public RulesResource getRules() {
    return rules;
  }

  public void setRules(RulesResource rules) {
    this.rules = rules;
  }

  public StageResource label(String label) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StageResource stageResource = (StageResource) o;
    return Objects.equals(this.resource, stageResource.resource) &&
        Objects.equals(this.stages, stageResource.stages) &&
        Objects.equals(this.rules, stageResource.rules) &&
        Objects.equals(this.label, stageResource.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resource, stages, rules, label);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StageResource {\n");
    
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    stages: ").append(toIndentedString(stages)).append("\n");
    sb.append("    rules: ").append(toIndentedString(rules)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
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

