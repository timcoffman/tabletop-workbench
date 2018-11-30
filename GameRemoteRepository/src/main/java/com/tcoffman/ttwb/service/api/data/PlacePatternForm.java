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
import com.tcoffman.ttwb.service.api.data.PartPatternForm;
import com.tcoffman.ttwb.service.api.data.PlacePatternForm;
import com.tcoffman.ttwb.service.api.data.QuantityPatternForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PlacePatternForm
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class PlacePatternForm {
  @JsonProperty("type")
  private String type = null;

  @JsonProperty("role")
  private String role = null;

  @JsonProperty("placeType")
  private String placeType = null;

  @JsonProperty("relationshipType")
  private String relationshipType = null;

  @JsonProperty("pattern")
  private PlacePatternForm pattern = null;

  @JsonProperty("part")
  private PartPatternForm part = null;

  @JsonProperty("quantity")
  private QuantityPatternForm quantity = null;

  @JsonProperty("patterns")
  private List<PlacePatternForm> patterns = null;

  @JsonProperty("exists")
  private Boolean exists = null;

  @JsonProperty("forward")
  private Boolean forward = null;

  @JsonProperty("related")
  private PlacePatternForm related = null;

  public PlacePatternForm type(String type) {
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

  public PlacePatternForm role(String role) {
    this.role = role;
    return this;
  }

   /**
   * Get role
   * @return role
  **/
  @ApiModelProperty(value = "")
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public PlacePatternForm placeType(String placeType) {
    this.placeType = placeType;
    return this;
  }

   /**
   * Get placeType
   * @return placeType
  **/
  @ApiModelProperty(value = "")
  public String getPlaceType() {
    return placeType;
  }

  public void setPlaceType(String placeType) {
    this.placeType = placeType;
  }

  public PlacePatternForm relationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
    return this;
  }

   /**
   * Get relationshipType
   * @return relationshipType
  **/
  @ApiModelProperty(value = "")
  public String getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
  }

  public PlacePatternForm pattern(PlacePatternForm pattern) {
    this.pattern = pattern;
    return this;
  }

   /**
   * Get pattern
   * @return pattern
  **/
  @ApiModelProperty(value = "")
  public PlacePatternForm getPattern() {
    return pattern;
  }

  public void setPattern(PlacePatternForm pattern) {
    this.pattern = pattern;
  }

  public PlacePatternForm part(PartPatternForm part) {
    this.part = part;
    return this;
  }

   /**
   * Get part
   * @return part
  **/
  @ApiModelProperty(value = "")
  public PartPatternForm getPart() {
    return part;
  }

  public void setPart(PartPatternForm part) {
    this.part = part;
  }

  public PlacePatternForm quantity(QuantityPatternForm quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * Get quantity
   * @return quantity
  **/
  @ApiModelProperty(value = "")
  public QuantityPatternForm getQuantity() {
    return quantity;
  }

  public void setQuantity(QuantityPatternForm quantity) {
    this.quantity = quantity;
  }

  public PlacePatternForm patterns(List<PlacePatternForm> patterns) {
    this.patterns = patterns;
    return this;
  }

  public PlacePatternForm addPatternsItem(PlacePatternForm patternsItem) {
    if (this.patterns == null) {
      this.patterns = new ArrayList<>();
    }
    this.patterns.add(patternsItem);
    return this;
  }

   /**
   * Get patterns
   * @return patterns
  **/
  @ApiModelProperty(value = "")
  public List<PlacePatternForm> getPatterns() {
    return patterns;
  }

  public void setPatterns(List<PlacePatternForm> patterns) {
    this.patterns = patterns;
  }

  public PlacePatternForm exists(Boolean exists) {
    this.exists = exists;
    return this;
  }

   /**
   * Get exists
   * @return exists
  **/
  @ApiModelProperty(value = "")
  public Boolean isExists() {
    return exists;
  }

  public void setExists(Boolean exists) {
    this.exists = exists;
  }

  public PlacePatternForm forward(Boolean forward) {
    this.forward = forward;
    return this;
  }

   /**
   * Get forward
   * @return forward
  **/
  @ApiModelProperty(value = "")
  public Boolean isForward() {
    return forward;
  }

  public void setForward(Boolean forward) {
    this.forward = forward;
  }

  public PlacePatternForm related(PlacePatternForm related) {
    this.related = related;
    return this;
  }

   /**
   * Get related
   * @return related
  **/
  @ApiModelProperty(value = "")
  public PlacePatternForm getRelated() {
    return related;
  }

  public void setRelated(PlacePatternForm related) {
    this.related = related;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlacePatternForm placePatternForm = (PlacePatternForm) o;
    return Objects.equals(this.type, placePatternForm.type) &&
        Objects.equals(this.role, placePatternForm.role) &&
        Objects.equals(this.placeType, placePatternForm.placeType) &&
        Objects.equals(this.relationshipType, placePatternForm.relationshipType) &&
        Objects.equals(this.pattern, placePatternForm.pattern) &&
        Objects.equals(this.part, placePatternForm.part) &&
        Objects.equals(this.quantity, placePatternForm.quantity) &&
        Objects.equals(this.patterns, placePatternForm.patterns) &&
        Objects.equals(this.exists, placePatternForm.exists) &&
        Objects.equals(this.forward, placePatternForm.forward) &&
        Objects.equals(this.related, placePatternForm.related);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, role, placeType, relationshipType, pattern, part, quantity, patterns, exists, forward, related);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlacePatternForm {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    placeType: ").append(toIndentedString(placeType)).append("\n");
    sb.append("    relationshipType: ").append(toIndentedString(relationshipType)).append("\n");
    sb.append("    pattern: ").append(toIndentedString(pattern)).append("\n");
    sb.append("    part: ").append(toIndentedString(part)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    patterns: ").append(toIndentedString(patterns)).append("\n");
    sb.append("    exists: ").append(toIndentedString(exists)).append("\n");
    sb.append("    forward: ").append(toIndentedString(forward)).append("\n");
    sb.append("    related: ").append(toIndentedString(related)).append("\n");
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

