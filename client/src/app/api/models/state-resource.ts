/* tslint:disable */
import { PartsResource } from './parts-resource';
import { LogEntriesResource } from './log-entries-resource';
import { ParticipantsResource } from './participants-resource';
import { RelationshipsResource } from './relationships-resource';
import { OperationPatternSetsResource } from './operation-pattern-sets-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface StateResource {
  resource?: string;
  model?: string;
  label?: string;
  parts?: PartsResource;
  log?: LogEntriesResource;
  participants?: ParticipantsResource;
  relationships?: RelationshipsResource;
  allowedOperations?: OperationPatternSetsResource;
  stage?: string;
  meta?: ResourceMetaData;
}
