/**
 * file for module.
 * Module in Angular JS is something that holds similar files 
 */
'use strict';
var App = angular.module('myApp',['angularUtils.directives.dirPagination']);

App.directive('fileModel', ['$parse', function ($parse) {
	return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

/*App.directive('fileField', function() {
	  var template = '<input type="file" name="files"/>';
	  return function( scope, elem, attrs ) {
	    var selector = $( template );
	    elem.append(selector);
	    selector.bind('change', function( event ) {
	      scope.$apply(function() {
	        scope[ attrs.fileField ] = event.originalEvent.target.files;
	      });
	    });
	    scope.$watch(attrs.fileField, function(file) {
	      selector.val(file);
	    });
	  };
	});
*/