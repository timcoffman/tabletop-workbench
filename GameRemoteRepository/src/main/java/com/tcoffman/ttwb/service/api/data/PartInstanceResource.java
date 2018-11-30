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
 * PartInstanceResource
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class PartInstanceResource {
  @JsonProperty("resource")
  private String resource = null;

  @JsonProperty("role")
  private String role = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("prototypeResource")
  private String prototypeResource = null;

  public PartInstanceResource resource(String resource) {
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

  public PartInstanceResource role(String role) {
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

  public PartInstanceResource label(String label) {
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

  public PartInstanceResource prototypeResource(String prototypeResource) {
    this.prototypeResource = prototypeResource;
    return this;
  }

   /**
   * Get prototypeResource
   * @return prototypeResource
  **/
  @ApiModelProperty(value = "")
  public String getPrototypeResource() {
    return prototypeResource;
  }

  public void setPrototypeResource(String prototypeResource) {
    this.prototypeResource = prototypeResource;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartInstanceResource partInstanceResource = (PartInstanceResource) o;
    return Objects.equals(this.resource, partInstanceResource.resource) &&
        Objects.equals(this.role, partInstanceResource.role) &&
        Objects.equals(this.label, partInstanceResource.label) &&
        Objects.equals(this.prototypeResource, partInstanceResource.prototypeResource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resource, role, label, prototypeResource);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartInstanceResource {\n");
    
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    prototypeResource: ").append(toIndentedString(prototypeResource)).append("\n");
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
