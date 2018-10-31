package com.tcoffman.ttwb.web.resource;

import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableMap;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourceMetaData {

	private String m_type;
	private String m_id;
	protected String m_label;
	private Date m_lastModified;

	private final Map<String, Set<String>> m_keywords = new HashMap<>();

	private ResourceMetaData() {
	}

	public String getType() {
		return m_type;
	}

	public String getId() {
		return m_id;
	}

	public String getLabel() {
		return m_label;
	}

	public Date getLastModified() {
		return m_lastModified;
	}

	public Map<String, Set<String>> getKeywords() {
		return unmodifiableMap(m_keywords);
	}

	public interface Builder {
		Builder identifiedBy(String identifier);

		Builder labelled(String label);

		Builder lastModified(Date lastModified);

		default Builder keyword(String category, String keyword) {
			return keywords(category, singleton(keyword));
		}

		Builder keywords(String category, Collection<String> keywords);

		ResourceMetaData build();
	}

	public static final Builder forResource(AbstractResource resource) {
		return new Builder() {
			private final ResourceMetaData m_resourceMetaData = new ResourceMetaData();

			@Override
			public Builder identifiedBy(String identifier) {
				m_resourceMetaData.m_id = identifier;
				return this;
			}

			@Override
			public Builder labelled(String label) {
				m_resourceMetaData.m_label = label;
				return this;
			}

			@Override
			public Builder lastModified(Date lastModified) {
				m_resourceMetaData.m_lastModified = lastModified;
				return this;
			}

			@Override
			public Builder keywords(String category, Collection<String> keywords) {
				Set<String> k = m_resourceMetaData.m_keywords.get(category);
				if (null == k)
					m_resourceMetaData.m_keywords.put(category, k = new HashSet<>());
				k.addAll(keywords);
				return this;
			}

			@Override
			public ResourceMetaData build() {
				m_resourceMetaData.m_type = resource.getClass().getSimpleName();
				return m_resourceMetaData;
			}

		};
	}

}
