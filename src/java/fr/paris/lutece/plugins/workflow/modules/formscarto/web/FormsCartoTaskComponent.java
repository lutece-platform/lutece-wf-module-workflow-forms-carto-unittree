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
package fr.paris.lutece.plugins.workflow.modules.formscarto.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

import fr.paris.lutece.plugins.carto.business.DataLayer;
import fr.paris.lutece.plugins.carto.business.DataLayerHome;
import fr.paris.lutece.plugins.carto.business.DataLayerMapTemplateHome;
import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.genericattributes.business.Field;
import fr.paris.lutece.plugins.genericattributes.service.entrytype.IEntryTypeService;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitService;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitUserService;
import fr.paris.lutece.plugins.unittree.service.unit.UnitService;
import fr.paris.lutece.plugins.workflow.modules.forms.business.EditFormResponseConfigValue;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormResponseConfigValueCarto;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormResponseConfigValueCartoHome;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormsCartoUnitTree;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormsCartoUnitTreeHome;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.FormsCartoTaskConfig;
import fr.paris.lutece.plugins.workflow.web.task.AbstractTaskComponent;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
* This Web component class make link between the web interface (Config form and/or Task execution form) 
* and the task configuration business logic.
*/
//public class FormsCartoTaskComponent extends AbstractTaskComponent
public class FormsCartoTaskComponent extends NoFormTaskComponent
{

    // Templates
    private static final String TEMPLATE_TASK_INFORMATION = "admin/plugins/workflow/modules/formscarto/task_formscarto_information.html";  //TODO: Create template file ( See getDisplayTaskInformaiton function ).
    private static final String TEMPLATE_TASK_CONFIG = "admin/plugins/workflow/modules/formscarto/task_formscarto_config.html";

	//MARKERS
    private static final String MARK_CONFIG = "config";
    private static final String MARK_LST_FORM = "lstForms";
    
    private static final String MARK_ID_FORM = "id_form";
    private static final String MARK_ID_STEP = "id_step";
    private static final String MARK_ID_QUESTION = "id_question";
    private static final String MARK_QUESTION_LIST = "question_list";
    private static final String MARK_MAPPING_LIST = "mapping_list";
    private static final String MARK_MULTIFORM = "multiform";
    private static final String MARK_CODE_LIST = "code_list";
    private static final String MARK_RESPONSE_LIST = "response_list";
    private static final String MARK_STEP_LIST = "list_step";
    
    private static final String MARK_ID_VALUE_LIST_CLOSE = "id_list_close";
    private static final String MARK_ID_VALUE_CARTO_LAYER = "id_carto_layer";
    private static final String MARK_ID_VALUE_UNITTREE = "id_unittree";
    private static final String MARK_LIST_CARTO_FORMS_UNITTREE = "lst_carto_forms_unittree";
    
    private static final String MARK_ID_FORM_CARTO = "id_form_carto";
    private static final String MARK_ID_STEP_CARTO = "id_step_carto";
    private static final String MARK_ID_QUESTION_CARTO = "id_question_carto";
    private static final String MARK_ID_QUESTION_UNITTREE = "id_question_unittree";
    private static final String MARK_QUESTION_LIST_CARTO = "question_list_carto";
    private static final String MARK_QUESTION_LIST_UNITTREE = "question_list_unittree";
    private static final String MARK_MAPPING_LIST_CARTO = "mapping_list_carto";
    private static final String MARK_MULTIFORM_CARTO = "multiform_carto";
    private static final String MARK_CODE_LIST_CARTO = "code_list_carto";
    private static final String MARK_RESPONSE_LIST_CARTO = "response_list_carto";
    private static final String MARK_STEP_LIST_CARTO = "list_step_carto";
    private static final String MARK_LAYER_LIST_CARTO = "list_datalayer";
    private static final String MARK_VALUE_LIST_CARTO = "list_value_carto";
    private static final String MARK_UNITTREE_LIST_CARTO = "list_unittree_carto";
    
 // Parameters
    private static final String PARAMETER_ACTION = "apply";
    private static final String PARAMETER_MULTIFORM = "multiform";
    private static final String PARAMETER_FORM = "form_select";
    private static final String PARAMETER_STEP = "step_select";
    private static final String PARAMETER_QUESTION = "question_select";
    private static final String PARAMETER_MAPPING_ID = "mapping_id";
    private static final String PARAMETER_CODE = "code_select";
    private static final String PARAMETER_RESPONSE = "response_select";
    
    private static final String PARAMETER_ACTION_CARTO = "apply_carto";
    private static final String PARAMETER_MULTIFORM_CARTO = "multiform_carto";
    private static final String PARAMETER_FORM_CARTO = "form_select_carto";
    private static final String PARAMETER_STEP_CARTO = "step_select_carto";
    private static final String PARAMETER_QUESTION_CARTO = "question_select_carto";
    private static final String PARAMETER_QUESTION_UNITTREE = "question_select_unittree";
    private static final String PARAMETER_MAPPING_ID_CARTO = "mapping_id_carto";
    private static final String PARAMETER_CODE_CARTO = "code_select_carto";
    private static final String PARAMETER_RESPONSE_CARTO = "response_select_carto";
    
    private static final String PARAMETER_VALUE_LIST_CLOSE = "value_select_carto";
    private static final String PARAMETER_VALUE_LAYER_CARTO = "layer_select_carto";
    private static final String PARAMETER_VALUE_UNITTREE = "layer_select_unittree";
    private static final String PARAMETER_ID_AFFECTATION = "id_unit";
    
    private static final String PARAM_CARTO_ID_WORKFLOW = "field_carto";

    // Action
    private static final String ACTION_SELECT_FORM = "select_form_config";
    private static final String ACTION_SELECT_MULTIFORM = "select_multiform";
    private static final String ACTION_SELECT_STEP = "select_step_config";
    private static final String ACTION_SELECT_QUESTION = "select_question_config";
    private static final String ACTION_SELECT_RESPONSE = "select_response_config";
    private static final String ACTION_REMOVE_MAPPING = "delete_mapping";
    private static final String ACTION_SELECT_CODE = "select_code";
    
    private static final String ACTION_SELECT_FORM_CARTO = "select_form_config_carto";
    private static final String ACTION_SELECT_MULTIFORM_CARTO = "select_multiform_carto";
    private static final String ACTION_SELECT_STEP_CARTO = "select_step_config_carto";
    private static final String ACTION_SELECT_QUESTION_CARTO = "select_question_config_carto";
    private static final String ACTION_SELECT_QUESTION_UNITTREE = "select_question_config_unittree";
    private static final String ACTION_SELECT_RESPONSE_CARTO = "select_response_config_carto";
    private static final String ACTION_REMOVE_MAPPING_CARTO = "delete_mapping_carto";
    private static final String ACTION_SELECT_CODE_CARTO = "select_code_carto";
    private static final String ACTION_SELECT_NEW_AFFECTATION = "select_affectation";
    
    // Messages
    private static final String MESSAGE_INFORMATION_TASK = "module.workflow.formscarto.message.task.information"; //TODO: defined message (key:message.task.information) in messages properties files. ( See getDisplayTaskInformaiton function ).
    
    private EditFormResponseConfigValue _configValue;
    private EditFormResponseConfigValue _configValueCarto;
    private EditFormsCartoUnitTree _configFormsCartoUnitTree;
    private FormsCartoTaskConfig _config;
	
	////////////////////////////Config form/////////////////////////////
	
    /**
     * Returns task configuration form
     * 
     * @param request
     *            request
     * @param locale
     *            locale
     * @param task
     *            the task
     * @return the information which must be displayed in the task configuration
     */
	@Override
	public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task ) 
	{	
		FormsCartoTaskConfig config = this.getTaskConfigService( ).findByPrimaryKey( task.getId( ) );
		
		if ( request.getParameter( PARAMETER_ID_AFFECTATION ) != null )
		{
			int id_affectation = Integer.valueOf( request.getParameter( PARAMETER_ID_AFFECTATION ) );
			EditFormsCartoUnitTreeHome.remove( id_affectation );
		}
		
		_config = this.getTaskConfigService( ).findByPrimaryKey( task.getId( ) ); 
		
		if( _config==null )
		{
			_config=new FormsCartoTaskConfig( );
		}
		
		/*
		for ( EditFormsCartoUnitTree unit : EditFormsCartoUnitTreeHome.getEditFormsCartoUnitTreesListByIdConfig( _config.getIdTask() ) )
		{
			_config.addEditFormsCartoUnitTree( unit );
		}
		*/
		
		if ( _configValue == null )
        {
            _configValue = new EditFormResponseConfigValue( );
        }
		
		if ( _configValueCarto == null )
        {
			_configValueCarto = new EditFormResponseConfigValue( );
        }
		
		if ( _configFormsCartoUnitTree == null )
        {
			_configFormsCartoUnitTree = new EditFormsCartoUnitTree( );
        }
		
		
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_CONFIG, _config );
        model.put( MARK_LST_FORM, FormHome.getFormsReferenceList( ) );
        
        if ( _config.getForm( ) != null )
        {
            //model.put( MARK_ID_FORM, _configValue.getForm( ).getId( ) );
            //model.put( MARK_STEP_LIST, StepHome.getStepReferenceListByForm( _configValue.getForm( ).getId( ) ) );
            model.put( MARK_ID_FORM, _config.getForm( ).getId( ) );
            model.put( MARK_STEP_LIST, StepHome.getStepReferenceListByForm( _config.getForm( ).getId( ) ) );
        }
        if ( _config.getStep( ) != null )
        {
            model.put( MARK_ID_STEP, _config.getStep( ).getId( ) );
            model.put( MARK_QUESTION_LIST, getQuestionReferenceList( _config.getStep( ).getId( ), false ) );
        }
        if ( _config.getQuestionListValueClosed( ) != null )
        {
            model.put( MARK_ID_QUESTION, _config.getQuestionListValueClosed( ).getId( ) );
            model.put( MARK_RESPONSE_LIST, getResponseReferenceList( _config.getQuestionListValueClosed( ).getId( ) ) );
            
            model.put( MARK_VALUE_LIST_CARTO, getListValueReferenceList( _config.getQuestionListValueClosed( ).getId( ) ) );
        }
        
        /*
        if ( _configValueCarto.getForm( ) != null )
        {
            model.put( MARK_ID_FORM_CARTO, _configValueCarto.getForm( ).getId( ) );
            model.put( MARK_STEP_LIST_CARTO, StepHome.getStepReferenceListByForm( _configValueCarto.getForm( ).getId( ) ) );
        }
        */
        if ( _config.getStep( ) != null )
        {
            model.put( MARK_ID_STEP_CARTO, _config.getStep( ).getId( ) );
            model.put( MARK_QUESTION_LIST_CARTO, getQuestionReferenceList( _config.getStep( ).getId( ), false ) );
        }
        
        if ( _config.getStep( ) != null )
        {
            model.put( MARK_ID_STEP_CARTO, _config.getStep( ).getId( ) );
            model.put( MARK_QUESTION_LIST_UNITTREE, getQuestionReferenceList( _config.getStep( ).getId( ), true ) );
        }
        
        if ( _config.getQuestionListLayerCarto( ) != null )
        {
            model.put( MARK_ID_QUESTION_CARTO, _config.getQuestionListLayerCarto( ).getId( ) );
            model.put( MARK_RESPONSE_LIST_CARTO, getResponseReferenceList( _config.getQuestionListLayerCarto( ).getId( ) ) );
            
            model.put( MARK_LAYER_LIST_CARTO, getLayerCartoReferenceList( _config.getQuestionListLayerCarto( ).getId( ) ) );
        }
        
        if ( _config.getQuestionListValueClosed( ) != null && _config.getQuestionListLayerCarto( ) != null )
        {
        	model.put( MARK_ID_VALUE_LIST_CLOSE, _configFormsCartoUnitTree.getFieldValueForms() );
        	model.put( MARK_ID_VALUE_CARTO_LAYER, _configFormsCartoUnitTree.getFieldCartoLayer() );
        	model.put( MARK_ID_VALUE_UNITTREE, _configFormsCartoUnitTree.getFieldUnittree() );
        	
        	model.put( MARK_LIST_CARTO_FORMS_UNITTREE, _config.getListEditFormsCartoUnitTree() );
        	//model.put( MARK_LIST_CARTO_FORMS_UNITTREE, getListValueReferenceList( _config.getQuestionUnitTree().getId( ) ) );
        	
        	//model.put( MARK_UNITTREE_LIST_CARTO, getUnitTreeList() );
        	if ( _config.getQuestionUnitTree() != null )
        		model.put( MARK_UNITTREE_LIST_CARTO, getListValueReferenceList( _config.getQuestionUnitTree().getId( ) ) );
        }
        
        if ( _config.getQuestionUnitTree( ) != null )
        {
            model.put( MARK_ID_QUESTION_UNITTREE, _config.getQuestionUnitTree().getId() );
        }
        
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_CONFIG, locale, model );
        return template.getHtml( );
	}
	
	 /**
     * Validate inputs of config form
     * 
     * @param config
     *            the config to validate
     * @param request
     *            the HTTP request
     * @return the JSP error if the config is not validated, an empty String otherwise
     */
	@Override
	public String validateConfig( ITaskConfig config, HttpServletRequest request )
	{
	    
		String field_carto = request.getParameter( PARAM_CARTO_ID_WORKFLOW );


        if ( StringUtils.isBlank( field_carto ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
        else
        if ( config instanceof FormsCartoTaskConfig )
        {
            final FormsCartoTaskConfig taskConfig = (FormsCartoTaskConfig) config;
            taskConfig.setFieldCarto(field_carto);

            // Check mandatory fields
            /*
            Set<ConstraintViolation<ITaskConfig>> constraintViolations = BeanValidationUtil.validate( taskConfig );

            if ( CollectionUtils.isNotEmpty( constraintViolations ) )
            {
                return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
            }
            */
        }
		
        return StringUtils.EMPTY;
		//return super.validateConfig( config, request );
	}

	/////////////////////////Task Form////////////////////////////
	
	/**
     * Returns tasks form
     * 
     * @param nIdResource
     *            the resource id
     * @param strResourceType
     *            the resource type
     * @param request
     *            request
     * @param locale
     *            locale
     * @param task
     *            the task
     * @return the information which must be displayed in the tasks form
     */
	@Override
	public String getDisplayTaskForm( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale, ITask task ) 
	{
        return null;
	}
	
    /**
     * validates the user input of task form associated to the task
     * 
     * @param nIdResource
     *            the resource id
     * @param strResourceType
     *            the resource type
     * @param request
     *            request
     * @param locale
     *            locale
     * @param task
     *            the task
     * @return null if there is no error in the task form else return the error message url
     */    
	@Override
	public String doValidateTask( int nIdRessource, String strResourceType, HttpServletRequest request, Locale locale, ITask task ) 
	{
		return null;
	}
    
	///////////////////////////Information Task//////////////////////////////

    /**
     * Returns the information stored for a document during the processing task
     * 
     * @param nIdHistory
     *            the document id
     * @param request
     *            the request
     * @param locale
     *            locale
     * @param task
     *            the task
     * @return the information stored during processing task
     */
	@Override
	public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task ) 
	{
			
         /*
        	This function is called in the WorkflowProvider class of the plugin lutece-wf-plugin-workflow. 
        	It allows to display information about task progression or ressource state 
            in the history section of a resource (e.g., form) to which a workflow tasks are associated. 
            
            1) If you don't want to display any information about the task, you can return null.
            return null;
            
            2) If you want to display a constant string, you have to use an internationalisation key i18n
            return I18nService.getLocalizedString( MESSAGE_INFORMATION_TASK, locale );
            
            3) If you want to display more than a constant string, you can create an html template with all information.
            //Add code to get task information
            // ...
            
            Map<String, Object> model = new HashMap<String, Object>( );
            model.put(...);

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_INFORMATION, locale, model );
            return template.getHtml( );
        */
        
        return null; //TODO: ADD MESSAGE INFORMATION
	}
	
	protected boolean isTaskBo( )
    {
        return true;
    }
	
	private ReferenceList getQuestionReferenceList( int idStep, boolean isBOonly )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( -1, "" );
        if ( idStep != -1 )
        {
            List<Question> questionList = QuestionHome.getQuestionsListByStep( idStep );
            for ( Question question : questionList )
            {
                if ( question.getEntry( ).isOnlyDisplayInBack( ) == isBOonly )
                {
                    refList.addItem( question.getId( ), question.getTitle( ) );
                }
            }
        }

        return refList;
    }
	
	@Override
    public String doSaveConfig( HttpServletRequest request, Locale locale, ITask task )
    {
		FormsCartoTaskConfig  config = getTaskConfigService( ).findByPrimaryKey( task.getId( ) );
		
        boolean create = config == null;
        if ( _config != null )
        	config = _config;
        if ( create )
        {
            config = new FormsCartoTaskConfig( );
            config.setIdTask( task.getId( ) );
            config.setFieldCarto( request.getParameter("field_carto") );
            config.setForm( FormHome.findByPrimaryKey( Integer.valueOf( request.getParameter( PARAMETER_FORM ) ) ) );
            if ( _configFormsCartoUnitTree != null )
            	config.addEditFormsCartoUnitTree( _configFormsCartoUnitTree );
        }

        
        
        String action = request.getParameter( PARAMETER_ACTION );
        if ( action != null )
        {
            doProcessAction( action, request );
        }
        
        if ( _configValue != null )
        {
        	_config.addConfigValue(_configValue);
        }
        if ( _configValueCarto != null )
        {
        	_config.addConfigValue(_configValueCarto);
        }

        if ( create )
        {
            getTaskConfigService( ).create( config );
        }
        else
        {
            getTaskConfigService( ).update( config );
        }
        
        /*
        _configValue = getTaskConfigService( ).findByPrimaryKey( task.getId( ) );
        create = _configValue == null;
        if ( create )
        {
        	_configValue = new EditFormResponseConfigValueCarto();
        	_configValue.setIdConfig( task.getId() );
        }
        
        
        action = request.getParameter( PARAMETER_ACTION );
        if ( action != null )
        {
            doProcessAction( action, request );
        }

        if ( create )
        {
            //getTaskConfigService( ).create( _configValue );
        	//EditFormResponseConfigValueCartoHome.create(_configValue);
        }
        else
        {
            //getTaskConfigService( ).update( _configValue );
        	//EditFormResponseConfigValueCartoHome.update(_configValue);
        }
        */
        
        //EditFormResponseConfigValueHome.create(_configValueCarto);
        
        return null;
    }
	
	private void doProcessAction( String action, HttpServletRequest request )
    {
        switch( action )
        {
            case ACTION_SELECT_FORM:
                _config = new FormsCartoTaskConfig();
                _config.setForm( FormHome.findByPrimaryKey( Integer.valueOf( request.getParameter( PARAMETER_FORM ) ) ) );
                break;
            case ACTION_SELECT_STEP:
                _config.setStep( StepHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_STEP ) ) ) );
                _config.setQuestionListLayerCarto(null);
                _config.setQuestionListLayerCarto(null);
                _config.setQuestionUnitTree(null);
                break;
            case ACTION_SELECT_QUESTION:
                _config.setQuestionListValueClosed( QuestionHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_QUESTION ) ) ) );
                break;
                /*
            case ACTION_REMOVE_MAPPING:
                int idToRemove = Integer.parseInt( request.getParameter( PARAMETER_MAPPING_ID ) );
                List<EditFormResponseConfigValue> newList = _config.getListConfigValues( ).stream( )
                        .filter( configValue -> configValue.getIdConfigValue( ) != idToRemove ).collect( Collectors.toList( ) );
                _config.setListConfigValues( newList );
                break;
             
            case ACTION_SELECT_FORM_CARTO:
                _configValueCarto = new EditFormResponseConfigValue( );
                _configValueCarto.setForm( FormHome.findByPrimaryKey( Integer.valueOf( request.getParameter( PARAMETER_FORM_CARTO ) ) ) );
                break;
            case ACTION_SELECT_STEP_CARTO:
            	_configValueCarto.setStep( StepHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_STEP_CARTO ) ) ) );
            	_configValueCarto.setQuestion( null );
                break;
            */
            case ACTION_SELECT_QUESTION_CARTO:
            	_config.setQuestionListLayerCarto( QuestionHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_QUESTION_CARTO ) ) ) );
                break;
            case ACTION_SELECT_QUESTION_UNITTREE:
            	_config.setQuestionUnitTree( QuestionHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_QUESTION_UNITTREE ) ) ) );
                break;
            /*
            case ACTION_SELECT_RESPONSE_CARTO:
            	_configValueCarto.setResponse( request.getParameter( PARAMETER_RESPONSE_CARTO ) );
                //_config.addConfigValue( _configValue );
            	_configValueCarto = new EditFormResponseConfigValue( );
                break;
            case ACTION_SELECT_CODE_CARTO:
            	_configValueCarto = new EditFormResponseConfigValue( );
            	_configValueCarto.setCode( request.getParameter( PARAMETER_CODE_CARTO ) );
                break;
            */
            case ACTION_SELECT_NEW_AFFECTATION:
            	_configFormsCartoUnitTree = new EditFormsCartoUnitTree();
            	_configFormsCartoUnitTree.setFieldValueForms( request.getParameter( PARAMETER_VALUE_LIST_CLOSE ) );
            	_configFormsCartoUnitTree.setFieldCartoLayer( request.getParameter( PARAMETER_VALUE_LAYER_CARTO ) );
            	_configFormsCartoUnitTree.setFieldUnittree( request.getParameter( PARAMETER_VALUE_UNITTREE ) );
            	_configFormsCartoUnitTree.setIdConfig( _config.getIdTask( ) );
            	_config.addEditFormsCartoUnitTree( _configFormsCartoUnitTree );
            	EditFormsCartoUnitTreeHome.create( _configFormsCartoUnitTree );
            default:
                break;
        }
    }
	
	public ReferenceList getResponseReferenceList( int idQuestion )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( "", "" );
        if ( idQuestion != -1 )
        {
            Question question = QuestionHome.findByPrimaryKey( idQuestion );
            for ( Field field : question.getEntry( ).getFields( ) )
            {
                if ( IEntryTypeService.FIELD_ANSWER_CHOICE.equals( field.getCode( ) ) )
                {
                    refList.addItem( field.getValue( ), field.getTitle( ) );
                }
            }
        }
        return refList;
    }
	
	public ReferenceList getLayerCartoReferenceList( int idQuestion )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( "", "" );
        if ( idQuestion != -1 )
        {        	
        	Question question = QuestionHome.findByPrimaryKey( idQuestion );
            for ( Field field : question.getEntry( ).getFields( ) )
            {
                if ( IEntryTypeService.FIELD_PROVIDER.equals( field.getCode( ) ) )
                {
                    String idMap = field.getValue();
                    List<DataLayer> dataLayerListByMapTemplateId = DataLayerMapTemplateHome.getDataLayerListByMapTemplateId( Integer.valueOf(idMap), true );
                    dataLayerListByMapTemplateId.addAll( DataLayerMapTemplateHome.getDataLayerListByMapTemplateId( Integer.valueOf(idMap), false ) );
                    for( DataLayer datalayer : dataLayerListByMapTemplateId )
                    {
                    	if ( datalayer.getGeometryType().getTechnicalName().equals("Polygon") )
                    		refList.addItem( datalayer.getId(), datalayer.getTitle() );
                    }
                }
            }
            
        }
        return refList;
    }
	
	public ReferenceList getListValueReferenceList( int idQuestion )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( "", "" );
        if ( idQuestion != -1 )
        {        	
        	Question question = QuestionHome.findByPrimaryKey( idQuestion );
            for ( Field field : question.getEntry( ).getFields( ) )
            {
                if ( "answer_choice".equals( field.getCode( ) ) )
                {
                	refList.addItem( field.getValue( ), field.getTitle( ) );
                }
            }
            
        }
        return refList;
    }
	
	public ReferenceList getUnitTreeList( )
    {
        ReferenceList refList = new ReferenceList( );
        refList.addItem( "", "" );
        //String BEAN_UNIT_USER_SERVICE = "unittree.unitUserService";
        //IUnitUserService _unitUserService = SpringContextService.getBean( BEAN_UNIT_USER_SERVICE );
     	IUnitService _unitService = SpringContextService.getBean( UnitService.BEAN_UNIT_SERVICE );
        
     	List<Unit> allUnits = _unitService.getAllUnits(true);
        
        for ( Unit unit : allUnits )
        {
        	refList.addItem( unit.getCode(), unit.getLabel() );
        }
        
        return refList;
    }
	
}