/* tslint:disable */
import { PartPatternForm } from './part-pattern-form';
import { QuantityPatternForm } from './quantity-pattern-form';
export interface PlacePatternForm {
  type?: string;
  part?: PartPatternForm;
  placeType?: string;
  relationshipType?: string;
  pattern?: PlacePatternForm;
  role?: string;
  quantity?: QuantityPatternForm;
  patterns?: Array<PlacePatternForm>;
  exists?: boolean;
  forward?: boolean;
  related?: PlacePatternForm;
}
