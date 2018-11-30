/* tslint:disable */
<<<<<<< HEAD
import { PartsResource } from './parts-resource';
import { LogEntriesResource } from './log-entries-resource';
=======
import { LogEntriesResource } from './log-entries-resource';
import { PartsResource } from './parts-resource';
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
import { ParticipantsResource } from './participants-resource';
import { RelationshipsResource } from './relationships-resource';
import { OperationPatternSetsResource } from './operation-pattern-sets-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface RunnerResource {
  resource?: string;
<<<<<<< HEAD
  label?: string;
  parts?: PartsResource;
  log?: LogEntriesResource;
=======
  log?: LogEntriesResource;
  parts?: PartsResource;
  label?: string;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  participants?: ParticipantsResource;
  relationships?: RelationshipsResource;
  allowedOperations?: OperationPatternSetsResource;
  meta?: ResourceMetaData;
}
