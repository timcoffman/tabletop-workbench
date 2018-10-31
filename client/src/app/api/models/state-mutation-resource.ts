/* tslint:disable */
import { ResourceMetaData } from './resource-meta-data';
export interface StateMutationResource {
  source?: string;
  relationshipType?: string;
  role?: string;
  label?: string;
  operationType?: 'SIGNAL' | 'MOVE' | 'ORIENT' | 'JOIN' | 'SPLIT';
  mutation?: string;
  destination?: string;
  meta?: ResourceMetaData;
}
