/* tslint:disable */
import { RolesResource } from './roles-resource';
import { ModelPluginsResource } from './model-plugins-resource';
import { PartPrototypesResource } from './part-prototypes-resource';
import { StagesResource } from './stages-resource';
import { PartInstancesResource } from './part-instances-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface ModelResource {
  resource?: string;
  roles?: RolesResource;
  plugins?: ModelPluginsResource;
  label?: string;
  partPrototypes?: PartPrototypesResource;
  stages?: StagesResource;
  parts?: PartInstancesResource;
  meta?: ResourceMetaData;
}
