/* tslint:disable */
<<<<<<< HEAD
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
=======
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
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  meta?: ResourceMetaData;
}
