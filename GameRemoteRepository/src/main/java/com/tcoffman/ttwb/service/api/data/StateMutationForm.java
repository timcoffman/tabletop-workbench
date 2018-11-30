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
import com.tcoffman.ttwb.service.api.data.StateOperationForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * StateMutationForm
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-16T14:53:59.275-06:00")
public class StateMutationForm {
  @JsonProperty("result")
  private String result = null;

  @JsonProperty("operations")
  private List<StateOperationForm> operations = null;

  @JsonProperty("role")
  private String role = null;

  public StateMutationForm result(String result) {
    this.result = result;
    return this;
  }

   /**
   * Get result
   * @return result
  **/
  @ApiModelProperty(value = "")
  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public StateMutationForm operations(List<StateOperationForm> operations) {
    this.operations = operations;
    return this;
  }

  public StateMutationForm addOperationsItem(StateOperationForm operationsItem) {
    if (this.operations == null) {
      this.operations = new ArrayList<>();
    }
    this.operations.add(operationsItem);
    return this;
  }

   /**
   * Get operations
   * @return operations
  **/
  @ApiModelProperty(value = "")
  public List<StateOperationForm> getOperations() {
    return operations;
  }

  public void setOperations(List<StateOperationForm> operations) {
    this.operations = operations;
  }

  public StateMutationForm role(String role) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StateMutationForm stateMutationForm = (StateMutationForm) o;
    return Objects.equals(this.result, stateMutationForm.result) &&
        Objects.equals(this.operations, stateMutationForm.operations) &&
        Objects.equals(this.role, stateMutationForm.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, operations, role);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StateMutationForm {\n");
    
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    operations: ").append(toIndentedString(operations)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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

