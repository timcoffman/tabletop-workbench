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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * QuantityPatternForm
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class QuantityPatternForm {
  @JsonProperty("type")
  private String type = null;

  @JsonProperty("min")
  private Integer min = null;

  @JsonProperty("max")
  private Integer max = null;

  public QuantityPatternForm type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public QuantityPatternForm min(Integer min) {
    this.min = min;
    return this;
  }

   /**
   * Get min
   * @return min
  **/
  @ApiModelProperty(value = "")
  public Integer getMin() {
    return min;
  }

  public void setMin(Integer min) {
    this.min = min;
  }

  public QuantityPatternForm max(Integer max) {
    this.max = max;
    return this;
  }

   /**
   * Get max
   * @return max
  **/
  @ApiModelProperty(value = "")
  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuantityPatternForm quantityPatternForm = (QuantityPatternForm) o;
    return Objects.equals(this.type, quantityPatternForm.type) &&
        Objects.equals(this.min, quantityPatternForm.min) &&
        Objects.equals(this.max, quantityPatternForm.max);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, min, max);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QuantityPatternForm {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
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

