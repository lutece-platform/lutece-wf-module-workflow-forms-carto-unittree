/*
 * Copyright (c) 2002-2025, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
 
package fr.paris.lutece.plugins.workflow.modules.formscarto.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;

import fr.paris.lutece.api.user.User;
import fr.paris.lutece.plugins.carto.business.DataLayer;
import fr.paris.lutece.plugins.carto.business.DataLayerHome;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.service.FormResponseService;
import fr.paris.lutece.plugins.genericattributes.business.Field;
import fr.paris.lutece.plugins.genericattributes.business.Response;
import fr.paris.lutece.plugins.genericattributes.business.ResponseFilter;
import fr.paris.lutece.plugins.genericattributes.business.ResponseHome;
import fr.paris.lutece.plugins.genericattributes.service.entrytype.IEntryTypeService;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchEngine;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchResult;
import fr.paris.lutece.plugins.search.solr.indexer.SolrItem;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormsCartoUnitTree;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.FormsCartoTaskConfig;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.util.ReferenceList;

/**
 * FormsCartoTask contains all the application code launched when your task is executed in the Lutece workflow. It implements the specific business logic 
 * via processTaskWithResult() while respecting the contract of the SimpleTask interface, which ensures its integration into the workflow framework. 
 */ 
public class FormsCartoTask extends SimpleTask  
{
	
	//Messages
    public static final String MESSAGE_TASK_TITLE = "module.workflow.formscarto.service.taskTitle"; 
    
    //Config
    private static final String BEAN_CONFIG_SERVICE = "workflow-formscarto.formsCartoTaskService"; //Must be equal to Service Bean Id in xml spring context file
            
    //Service
	@Inject
    @Named( BEAN_CONFIG_SERVICE )
    private ITaskConfigService _taskConfigService;
    
    
	/**
     * Initialize the task
     */
	@Override
    public void init( ) 
	{
        // TODO : Implement the initialization of the task here
    }
 
    /**
     * returns the task title
     * 
     * @param locale
     *            locale
     * @return the task title
     */
    @Override
    public String getTitle(Locale locale)
    {
    	return I18nService.getLocalizedString( MESSAGE_TASK_TITLE, locale );
    }
	
	
	/**
     * Process the task and send a boolean result : 
     *  - if true : next resource state will be the default "state_after"
     *  - if false : next resource state will be the alternative "state_after"
     *  
     * @param nIdResource
     * @param strResourceType
     * @param nIdResourceHistory
     * @param request
     * @param locale
     * @param user
     * @return true by default, false to set the alternative state
     */
	@Override
    public boolean processTaskWithResult( int nIdResource, String strResourceType, int nIdResourceHistory, HttpServletRequest request, Locale locale, User user ) 
    {
        //TODO: Implement the task processing here
		FormsCartoTaskConfig config = _taskConfigService.findByPrimaryKey( this.getId( ) );
		String strJsonGeolocPoly = null;
		String strValueUnitTree = null;
		String responseValueListClosed = null;
        if ( config != null )
        {
        	Question questionListLayerCarto = config.getQuestionListLayerCarto();
        	String responseValue = getResponseValue( config.getQuestionListLayerCarto().getId() );
        	
        	FormResponse formResponseValueList = FormResponseHome.findByPrimaryKey( nIdResource );
        	//formResponseValueList.getSteps().get(0).getQuestions().get(0).getEntryResponse().get(0).getResponseValue();
        	//List<FormQuestionResponse> lstQuestionResponse = FormQuestionResponseHome.findFormQuestionResponseByQuestion( config.getQuestionListValueClosed().getId() );
        	List<FormQuestionResponse> lstQuestionResponse = FormQuestionResponseHome.getFormQuestionResponseListByFormResponse( formResponseValueList.getId() );
        	for ( FormQuestionResponse fqr : lstQuestionResponse )
        	{
        		//responseValue = getResponseValue( fqr.getQuestion().getId() );
        		for ( Response ent : fqr.getEntryResponse() )
				{
        			//System.out.println("Le field est " + ent.getField( ));
        			System.out.println("Le field est " + ent.getEntry().getCode( ) );
        			System.out.println("La value est " + ent.getResponseValue());
        			
        			if ( ent.getResponseValue() != null && ent.getResponseValue().contains( "coordinates" ) )
        			{
        				responseValue = ent.getResponseValue();
        			}
        			
        			if ( ent.getEntry().getCode( ).equals( config.getQuestionListValueClosed().getCode() ) ) {
            			responseValueListClosed = ent.getResponseValue();
            		}
				}
        	}
        	
            
            /*
            List<FormQuestionResponse> listFormQuestionResponse = FormQuestionResponseHome
                    .getFormQuestionResponseListByFormResponse( formResponseValueList.getId( ) );
        	for ( FormQuestionResponse fqr : listFormQuestionResponse )
        	{
        		if ( fqr.getEntryResponse().get(0).getField().getCode( ).equals( config.getQuestionListValueClosed().getCode() ) ) {
        			responseValueListClosed = fqr.getEntryResponse().get(0).getField().getValue();
        		}
        	}
        	*/
        	
        	
        	
        	
        	//String responseValueListClosed = getResponseValue( config.getQuestionListValueClosed().getId() );
        	SolrSearchEngine engine = SolrSearchEngine.getInstance( );
        	
        	
        	List<EditFormsCartoUnitTree> listEditFormsCartoUnitTree = config.getListEditFormsCartoUnitTree();
        	boolean inside = false;
        	for ( EditFormsCartoUnitTree unit : listEditFormsCartoUnitTree )
        	{
        		if ( responseValueListClosed.equals( unit.getFieldValueForms() ) )
        		{
        		
	    			//SolrSearchEngine engine = SolrSearchEngine.getInstance( );
	
	    			Optional<DataLayer> datalayer = DataLayerHome.findByPrimaryKey( Integer.valueOf( unit.getFieldCartoLayer() ) );
	    		
	            	String[] strQuerySolr = {"(" + "DataLayer_text" + ":" + datalayer.get().getSolrTag() + ")"};
	            	List<SolrSearchResult> geolocSearchResults = engine.getGeolocSearchResults( "*:*", strQuerySolr, 100 );
	            	
	
	                for ( SolrSearchResult result : geolocSearchResults )
	                {
	                    Map<String, Object> dynamicFields = result.getDynamicFields( );
	
	                    String uid = result.getId( );
	
	                    for ( Entry<String, Object> entry : dynamicFields.entrySet( ) )
	                    {
	
	                        if ( !entry.getKey( ).endsWith( SolrItem.DYNAMIC_GEOJSON_FIELD_SUFFIX ) )
	                        {
	                            continue;
	                        }
	                        HashMap<String, Object> h = new HashMap<>( );
	                        strJsonGeolocPoly = (String) entry.getValue( );
	                    }
	                }
	                
	                
	                //Verifier si point dans poly
	             // Exemple de GeoJSON de type Polygon
	                String geoJsonPolygon = strJsonGeolocPoly;
	
	                // Lecture du GeoJSON en géométrie JTS
	                GeoJsonReader reader = new GeoJsonReader();
	                Geometry geometry = null;
	                Geometry point2 = null;
					try {
						geometry = reader.read(geoJsonPolygon);
						point2 = reader.read( responseValue ) ;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
	                // Création d'un point (longitude, latitude)
	                //GeometryFactory geometryFactory = new GeometryFactory();
	                //Point point = geometryFactory.createPoint(new Coordinate(2.333, 48.865));
	                
	                //inside = false;
	                // Vérification
	                if ( geometry != null )
	                	inside = geometry.contains(point2);
	                System.out.println("Le point est dans le polygone ? " + inside);
	                if ( inside )
	                {
	                	strValueUnitTree = unit.getFieldUnittree();
	                }
        		}
        	}
        	
        	List<FormQuestionResponse> listFormQuestionResponseToSave = new ArrayList<>( );
        	
        	FormResponse formResponse = FormResponseHome.findByPrimaryKey( nIdResource );
            if ( formResponse == null )
            {
                return false;
            }
        	
        	FormQuestionResponse questionResponse = new FormQuestionResponse( );
            Response response = new Response( );
            Question question = config.getQuestionUnitTree();
            response.setEntry( question.getEntry( ) );
            response.setResponseValue( strValueUnitTree );
            
            questionResponse.setEntryResponse( new ArrayList<Response>( ) );
            questionResponse.setQuestion( question );
            questionResponse.setIdStep( question.getIdStep( ) );
            questionResponse.setEntryResponse( Arrays.asList( response ) );
            questionResponse.setIdFormResponse( formResponse.getId( ) );
            
            questionResponse.getEntryResponse( ).get( 0 ).setResponseValue( strValueUnitTree );
            
            /*
            for ( Field field : questionResponse.getQuestion( ).getEntry( ).getFields( ) )
            {
                if ( field.getValue( ).equals( config.getResponse( ) ) )
                {
                    questionResponse.getEntryResponse( ).get( 0 ).setField( field );
                }
            }
            */
            
            listFormQuestionResponseToSave.add( questionResponse );
            
            FormsCartoTaskService formcartotaskservice = new FormsCartoTaskService();
            formcartotaskservice.saveResponses( formResponse, listFormQuestionResponseToSave );
            FormResponseService.getInstance( ).saveFormResponse( formResponse );
        	
        	/*
        	Form form = config.getForm();
        	List<FormQuestionResponse> lstQuestionResponse = FormQuestionResponseHome.findFormQuestionResponseByQuestion( config.getQuestionListValueClosed().getId() );
        	for ( FormQuestionResponse fqr : lstQuestionResponse )
        	{
        		for ( Response ent : fqr.getEntryResponse() )
				{
        			System.out.println("Le field est " + ent.getField( ));
        			System.out.println("La value est " + ent.getResponseValue());
				}
        	}
        	*/
        	
        	
        }
        if (strValueUnitTree != null)
        	return true;
        else
        	return false;
    }   
	
   /**
    * Anonymize taskInformation associate to the history
    * 
    * @param nIdHistory
    *            the document id
   */
   @Override
   public void doAnonymizeTaskInformation( int nIdHistory )
   {
	   //TODO : If necessary, implement specific anonymization.
   }
   
   public String getResponseValue( int idQuestion )
   {
       ReferenceList refList = new ReferenceList( );
       String strValueResponse;
       refList.addItem( "", "" );
       if ( idQuestion != -1 )
       {
           Question question = QuestionHome.findByPrimaryKey( idQuestion );
           
           
           List<Integer> idEntryList = new ArrayList<Integer>();
           idEntryList.add( question.getIdEntry() );
           ResponseFilter responsefilter = new ResponseFilter( );
           //responsefilter.setListIdEntry( idEntryList );
           
           
           
           List<Field> fields = question.getEntry( ).getFields( );
           for ( Field field : fields )
           {
               if ( IEntryTypeService.FIELD_ANSWER_CHOICE.equals( field.getCode( ) ) )
               {
                   //refList.addItem( field.getValue( ), field.getTitle( ) );
            	   return field.getValue();
               }
               if ( "coordinates_geojson".equals( field.getCode( ) ) )
               {
                   //refList.addItem( field.getValue( ), field.getTitle( ) );
            	   responsefilter.setIdEntry( question.getIdEntry() );
            	   responsefilter.setIdField( field.getIdField() );
                   List<Response> resp = ResponseHome.getResponseList( responsefilter );
            	   
                   if ( resp.size() > 0 )
                	   return resp.get(0).getResponseValue();
                   else
                	   return "";
            	   
            	   //return field.getValue();
               }
           }
       }
       return "";
   }
   


}