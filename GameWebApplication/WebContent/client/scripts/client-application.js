define(['jquery','handlebars','ApiClient','api/StatesApi','api/ModelsApi'], function($,Handlebars, ApiClient, StatesApi, ModelsApi) {
	window.nextRefId = 1 ;
	var ClientApplication = function() {
		
		this.apiClient = new ApiClient() ;
		this.statesApi = new StatesApi(this.apiClient) ;
		this.modelsApi = new ModelsApi(this.apiClient) ;

		this.path =  [] ;

		var self = this ;
		Handlebars.registerHelper( 'ref', function(location,opts) {
			
			var elementId = 'ref-' + window.nextRefId++ ;
			
			var dataFormat = opts.hash['format'] ;
			
			self.renderRef(location,dataFormat, function(resource) {
				$('#' + elementId).replaceWith( opts.fn(resource) );
			}) ;
			
			return "<div id='" + elementId + "'>fetching...</div>"; 
		}) ;
		
		this.bootstrap = function(document) {
			this.statesElement = $('.states',document) ;
			this.stateElement = $('.state.detail',document) ;
			
			var self = this ;
			
			this.stateElement
				.click( '.action-set button.clear', function(evt) { self.clearActionSet() } )
				.click( '.action-set button.auto', function(evt) { self.autoAdvance() } )
				.click( '.action-set button.submit', function(evt) { self.submitActionSet() } )
				;
			
			this.stateElement
				.click( '.action-builder button.clear', function(evt) { self.clearActionBuilder() } )
				.click( '.action-builder button.add', function(evt) { self.buildAction() } )
				;
			
			$('button.refresh',document).click( function(evt) { self.refreshStates() } ) ;
			self.loadStates() ;

			this.statesElement.click('.state',function(evt){
				var target = $(evt.target).closest('.state') ;
				var detailLocation = target.attr('data-location');
				self.loadState(detailLocation) ;
			}) ;
		} ;
		
		/*
		 * States -> list
		 *  - State -> state template
		 *   - Participants -> list
		 *    - Participant -> participant template
		 *   - Parts -> list
		 *    - Part -> part template
		 *     - Places -> list
		 *      - Place -> place template
		 *   - Allowed Actions -> list
		 *    - ActionPattern -> action pattern template
		 *     - 
		 */
		
		var stateTemplate = Handlebars.compile( $('#state-template').html() );
		this.loadState = function( stateLocation ) {
			var matchStateId = /states\/([^\/]+)$/.exec(stateLocation) ;
			
			this.stateElement.empty() ;
			this.stateElement.attr("data-location",stateLocation) ;
			this.stateElement.attr("data-id", matchStateId ? matchStateId[1] : null) ;
			
			if ( !matchStateId ) return ;
			
			var self = this ;
			var stateId = matchStateId[1] ;
			this.statesApi.get(stateId, function(msg,data,xhr) {
				$(stateTemplate(data)).appendTo( self.stateElement );
			}) ;
		} ;
		
		this.submitActionSet = function() {
			$('.action-set button.clear',this.stateElement).attr('disabled','disabled') ;
			$('.action-set button.submit',this.stateElement).attr('disabled','disabled') ;
		};
		
		this.autoAdvance = function(evt) {
			var stateId = this.stateElement.attr("data-id") ;
			
			this.statesApi.mutate( stateId, { body: {} }, function(msg,data,xhr){
				
				console.info(msg) ;
				console.info(data) ;
				
			}) ;
		};
		
		this.clearActionSet = function() {
			$('.action-set button.clear',this.stateElement).attr('disabled','disabled') ;
			$('.action-set button.submit',this.stateElement).attr('disabled','disabled') ;
		};
		
		this.addActionToSet = function(action) {
			$('.action-set button.clear',this.stateElement).attr('disabled','') ;
			$('.action-set button.submit',this.stateElement).attr('disabled','') ;
		};
		
		this.rebuildActionBuilder = function() {
			$('.action-builder button.clear',this.stateElement).attr('disabled','') ;
			$('.action-builder button.submit',this.stateElement).attr('disabled','') ;
		};
		
		this.clearActionBuilder = function() {
			$('.action-builder button.clear',this.stateElement).attr('disabled','disabled') ;
			$('.action-builder button.add',this.stateElement).attr('disabled','disabled') ;
		};

		this.buildAction = function() {
			this.clearActionBuilder() ;
		};
		
		var statesTemplate = Handlebars.compile( $('#states-template').html() );
		this.loadStates = function() {
			
			this.loadState(null) ;

			this.statesElement.empty() ;
			
			var self = this ;
			this.statesApi.getStates(function(msg,data,xhr) {
				$(statesTemplate(data)).appendTo( self.statesElement );
				self.rebuildActionBuilder() ;
			}) ;
		} ;
		
	} ;
	return new ClientApplication ;
});