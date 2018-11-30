/* tslint:disable */
import { StateOperationForm } from './state-operation-form';
export interface StateMutationForm {
  result?: string;
  role?: string;
  operations?: Array<StateOperationForm>;
}
