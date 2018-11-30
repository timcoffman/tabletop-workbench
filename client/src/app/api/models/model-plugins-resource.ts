/* tslint:disable */
import { ModelPluginResource } from './model-plugin-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface ModelPluginsResource {
  requiredPlugins?: Array<ModelPluginResource>;
  meta?: ResourceMetaData;
}
