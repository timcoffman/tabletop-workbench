/* tslint:disable */
import { QuantityPatternForm } from './quantity-pattern-form';
export interface PartPatternForm {
  type?: string;
  prototype?: string;
  pattern?: PartPatternForm;
  role?: string;
  quantity?: QuantityPatternForm;
  patterns?: Array<PartPatternForm>;
}
