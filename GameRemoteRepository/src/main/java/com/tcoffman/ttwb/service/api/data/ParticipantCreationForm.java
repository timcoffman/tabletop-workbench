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
 * ParticipantCreationForm
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class ParticipantCreationForm {
  @JsonProperty("role")
  private String role = null;

  @JsonProperty("user")
  private String user = null;

  public ParticipantCreationForm role(String role) {
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

  public ParticipantCreationForm user(String user) {
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(value = "")
  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParticipantCreationForm participantCreationForm = (ParticipantCreationForm) o;
    return Objects.equals(this.role, participantCreationForm.role) &&
        Objects.equals(this.user, participantCreationForm.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, user);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParticipantCreationForm {\n");
    
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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

