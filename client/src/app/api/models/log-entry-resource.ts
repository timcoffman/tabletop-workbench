/* tslint:disable */
import { StateMutationResource } from './state-mutation-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface LogEntryResource {
  resource?: string;
  label?: string;
  forward?: string;
<<<<<<< HEAD
  rollbackMutations?: Array<StateMutationResource>;
  forwardMutations?: Array<StateMutationResource>;
  rollback?: string;
=======
  forwardMutations?: Array<StateMutationResource>;
  rollback?: string;
  rollbackMutations?: Array<StateMutationResource>;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  meta?: ResourceMetaData;
}
