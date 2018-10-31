/* tslint:disable */
import { StateOperationForm } from './state-operation-form';
export interface StateMutationForm {
  result?: string;
  operations?: Array<StateOperationForm>;
  role?: string;
}
