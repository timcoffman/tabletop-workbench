/* tslint:disable */
import { ParticipantResource } from './participant-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface ParticipantsResource {
  participants?: Array<ParticipantResource>;
  meta?: ResourceMetaData;
}
