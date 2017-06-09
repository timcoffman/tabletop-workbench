package com.tcoffman.ttwb.state;

public class DefaultAuthorizationManager implements GameAuthorizationManager {

	@Override
	public String serializeAuthorization(Object authorization) {
		return authorization.toString();
	}

	@Override
	public Object deserializeAuthorization(String serializedAuthorization) {
		return serializedAuthorization;
	}

}
