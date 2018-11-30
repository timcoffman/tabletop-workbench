/* tslint:disable */
import { ResourceMetaData } from './resource-meta-data';
export interface StateMutationResource {
<<<<<<< HEAD
  relationshipType?: string;
  label?: string;
  source?: string;
  role?: string;
=======
  source?: string;
  relationshipType?: string;
  role?: string;
  label?: string;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  operationType?: 'SIGNAL' | 'MOVE' | 'ORIENT' | 'JOIN' | 'SPLIT';
  mutation?: string;
  destination?: string;
  meta?: ResourceMetaData;
}
