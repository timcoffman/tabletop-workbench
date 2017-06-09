package com.tcoffman.ttwb.state;

public interface GameAuthorizationManager {

	String serializeAuthorization(Object authorization);

	Object deserializeAuthorization(String serializedAuthorization);

}
