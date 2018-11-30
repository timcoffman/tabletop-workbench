package com.tcoffman.ttwb.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.jdbi.v3.core.Jdbi;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

@Provider
public class ApplicationStateFilter implements ContainerResponseFilter {

	private boolean schemaOk = false;

	@Inject
	Jdbi jdbi;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if (!schemaOk)
			checkSchema();
	}

	private void checkSchema() {
		jdbi.inTransaction(tx -> {
			try {
				final Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(tx.getConnection()));
				final Liquibase lq = new Liquibase("db/changelog/db.changelog-master.xml", new ClassLoaderResourceAccessor(), db);
				final List<ChangeSet> changeSets = lq.listUnrunChangeSets(new Contexts(), new LabelExpression(), true);
				if (changeSets.isEmpty())
					schemaOk = true;
				else
					throw new InternalServerErrorException(String.format("schema not up-to-date: %1$d un-run change set(s)", changeSets.size()));
			} catch (final LiquibaseException ex) {
				throw new InternalServerErrorException(ex);
			}
			return null;
		});
	}
}
