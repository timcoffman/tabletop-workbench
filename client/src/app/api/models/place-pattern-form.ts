/* tslint:disable */
import { PartPatternForm } from './part-pattern-form';
import { QuantityPatternForm } from './quantity-pattern-form';
export interface PlacePatternForm {
  type?: string;
  placeType?: string;
  relationshipType?: string;
  pattern?: PlacePatternForm;
  part?: PartPatternForm;
  role?: string;
  quantity?: QuantityPatternForm;
  patterns?: Array<PlacePatternForm>;
  exists?: boolean;
  forward?: boolean;
  related?: PlacePatternForm;
}
