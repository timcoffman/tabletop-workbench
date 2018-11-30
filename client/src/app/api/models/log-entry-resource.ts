/* tslint:disable */
import { StateMutationResource } from './state-mutation-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface LogEntryResource {
  resource?: string;
  label?: string;
  forward?: string;
  rollbackMutations?: Array<StateMutationResource>;
  forwardMutations?: Array<StateMutationResource>;
  rollback?: string;
  meta?: ResourceMetaData;
}
