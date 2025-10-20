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

import java.util.List;

import javax.inject.Inject;

import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseStep;
import fr.paris.lutece.plugins.forms.business.FormResponseStepHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.forms.service.EntryServiceManager;
import fr.paris.lutece.plugins.forms.service.FormService;
import fr.paris.lutece.plugins.forms.util.FormsConstants;
import fr.paris.lutece.plugins.forms.web.entrytype.IEntryDataService;
import fr.paris.lutece.plugins.workflow.modules.forms.business.EditFormResponseConfigValue;
import fr.paris.lutece.plugins.workflow.modules.forms.business.EditFormResponseConfigValueHome;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormResponseConfigCarto;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormResponseConfigValueCarto;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormResponseConfigValueCartoHome;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormsCartoUnitTree;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.EditFormsCartoUnitTreeHome;
import fr.paris.lutece.plugins.workflow.modules.formscarto.business.FormsCartoTaskConfig;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig;
import fr.paris.lutece.plugins.workflowcore.service.config.TaskConfigService;

/**
 * This is the service class. The TaskConfigService class allows you to directly inherit the create, update, 
 * and delete functions that link to the DAO. Search functions such as findByPrimaryKey are also available through the TaskConfigService class.
 */ 
public class FormsCartoTaskService extends TaskConfigService   
{
	//TODO : Add service code
	@Inject
    private FormService _formService;
	
	@Override
    public FormsCartoTaskConfig findByPrimaryKey( int nIdTask )
    {
        /*
		EditFormResponseConfigCarto config = super.findByPrimaryKey( nIdTask );
        if ( config != null )
        {
            config.setListConfigValues( EditFormResponseConfigValueCartoHome.findByConfig( config.getIdConfig( ) ) );
        }
        */
		
		FormsCartoTaskConfig config = super.findByPrimaryKey( nIdTask );
		
        return config;
    }

    @Override
    public void create( ITaskConfig config )
    {
        
    	//EditFormResponseConfigCarto conf = (EditFormResponseConfigCarto) config;
    	FormsCartoTaskConfig conf = (FormsCartoTaskConfig) config;
        super.create( conf );
        for ( EditFormsCartoUnitTree configValue : conf.getListEditFormsCartoUnitTree())
        {
            configValue.setIdConfig( conf.getIdConfig( ) );
            //EditFormsCartoUnitTreeHome.create( configValue );
        }
        
    	//FormsCartoTaskConfig conf = (FormsCartoTaskConfig) config;
        //super.create( conf );
    }

    @Override
    public void update( ITaskConfig config )
    {
        /*
    	EditFormResponseConfigCarto conf = (EditFormResponseConfigCarto) config;
        EditFormResponseConfigValueCartoHome.removeByConfig( conf.getIdConfig( ) );
        super.update( config );

        for ( EditFormResponseConfigValueCarto configValue : conf.getListConfigValues( ) )
        {
            configValue.setIdConfig( conf.getIdConfig( ) );
            EditFormResponseConfigValueCartoHome.create( configValue );
        }
        */
    	
    	FormsCartoTaskConfig conf = (FormsCartoTaskConfig) config;
        //EditFormResponseConfigValueCartoHome.removeByConfig( conf.getIdConfig( ) );
        super.update( config );
        /*
        for ( EditFormsCartoUnitTree configValue : conf.getListEditFormsCartoUnitTree())
        {
            configValue.setIdConfig( conf.getIdConfig( ) );
            if ( configValue.getFieldValueForms() != null )
            	EditFormsCartoUnitTreeHome.create( configValue );
        }
        */
    }

    @Override
    public void remove( int nIdTask )
    {
        EditFormResponseConfigCarto config = super.findByPrimaryKey( nIdTask );
        if ( config != null )
        {
            EditFormResponseConfigValueCartoHome.removeByConfig( config.getIdConfig( ) );
        }
        super.remove( nIdTask );
    }
    
    public void saveResponses( FormResponse formResponse, List<FormQuestionResponse> listFormQuestionResponse )
    {
        for ( FormQuestionResponse formQuestionResponse : listFormQuestionResponse )
        {
            saveStep( formQuestionResponse );
            saveResponses( formQuestionResponse );
        }
        FormService formService  = new FormService();
        formService.fireFormResponseEventUpdate( formResponse );
    }
    
    /**
     * Saves the step associated to the specified form question response
     * 
     * @param formQuestionResponse
     *            the form question response
     */
    private void saveStep( FormQuestionResponse formQuestionResponse )
    {
        List<FormResponseStep> listFormResponseStep = FormResponseStepHome.findStepsByFormResponse( formQuestionResponse.getIdFormResponse( ) );
        boolean bFormResponseStepFound = listFormResponseStep.stream( )
                .anyMatch( formResponseStep -> formResponseStep.getStep( ).getId( ) == formQuestionResponse.getIdStep( ) );

        if ( !bFormResponseStepFound )
        {
            FormResponseStep formResponseStep = new FormResponseStep( );
            formResponseStep.setFormResponseId( formQuestionResponse.getIdFormResponse( ) );
            formResponseStep.setStep( StepHome.findByPrimaryKey( formQuestionResponse.getIdStep( ) ) );
            formResponseStep.setOrder( FormsConstants.ORDER_NOT_SET );

            FormResponseStepHome.create( formResponseStep );
        }
    }

    /**
     * Saves the question associated to the specified form question response
     * 
     * @param formQuestionResponse
     *            the form question response
     */
    private void saveResponses( FormQuestionResponse formQuestionResponse )
    {
        Question question = formQuestionResponse.getQuestion( );
        IEntryDataService dataService = EntryServiceManager.getInstance( ).getEntryDataService( question.getEntry( ).getEntryType( ) );
        dataService.save( formQuestionResponse );
    }
}