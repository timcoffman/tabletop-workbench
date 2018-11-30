/* tslint:disable */
import { ResourceMetaData } from './resource-meta-data';
export interface StateMutationResource {
  relationshipType?: string;
  label?: string;
  source?: string;
  role?: string;
  operationType?: 'SIGNAL' | 'MOVE' | 'ORIENT' | 'JOIN' | 'SPLIT';
  mutation?: string;
  destination?: string;
  meta?: ResourceMetaData;
}
