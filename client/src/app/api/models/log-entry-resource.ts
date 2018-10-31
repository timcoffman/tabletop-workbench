/* tslint:disable */
import { StateMutationResource } from './state-mutation-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface LogEntryResource {
  resource?: string;
  label?: string;
  forward?: string;
  forwardMutations?: Array<StateMutationResource>;
  rollback?: string;
  rollbackMutations?: Array<StateMutationResource>;
  meta?: ResourceMetaData;
}
