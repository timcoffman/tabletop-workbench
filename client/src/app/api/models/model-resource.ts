/* tslint:disable */
import { PartInstancesResource } from './part-instances-resource';
import { RolesResource } from './roles-resource';
import { ModelPluginsResource } from './model-plugins-resource';
import { StagesResource } from './stages-resource';
import { PartPrototypesResource } from './part-prototypes-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface ModelResource {
  resource?: string;
  parts?: PartInstancesResource;
  roles?: RolesResource;
  plugins?: ModelPluginsResource;
  stages?: StagesResource;
  partPrototypes?: PartPrototypesResource;
  label?: string;
  meta?: ResourceMetaData;
}
