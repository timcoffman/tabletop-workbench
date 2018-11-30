/* tslint:disable */
import { PartPatternForm } from './part-pattern-form';
import { QuantityPatternForm } from './quantity-pattern-form';
export interface PlacePatternForm {
  type?: string;
<<<<<<< HEAD
  placeType?: string;
  relationshipType?: string;
  pattern?: PlacePatternForm;
  part?: PartPatternForm;
=======
  part?: PartPatternForm;
  placeType?: string;
  relationshipType?: string;
  pattern?: PlacePatternForm;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  role?: string;
  quantity?: QuantityPatternForm;
  patterns?: Array<PlacePatternForm>;
  exists?: boolean;
  forward?: boolean;
  related?: PlacePatternForm;
}
