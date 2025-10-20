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

package fr.paris.lutece.plugins.workflow.modules.formscarto.business;

import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.StepHome;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;

import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;
import java.sql.Types;

/**
 * This class provides Data Access methods for FormsCartoTaskConfig objects
 */
public class FormsCartoTaskConfigDAO implements ITaskConfigDAO<FormsCartoTaskConfig>
{
    // Constants
    private static final String SQL_QUERY_INSERT = "INSERT INTO workflow_task_formscarto_config ( id_task, id_form, id_step, id_question_list_value_closed, id_question_list_layer_carto, id_question_unit_tree ) VALUES ( ?, ?, ?, ?, ?, ?)";
    private static final String SQL_QUERY_DELETE = "DELETE FROM workflow_task_formscarto_config WHERE id_task = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE workflow_task_formscarto_config SET id_form = ?, id_step = ?, id_question_list_value_closed = ?, id_question_list_layer_carto = ?, id_question_unit_tree = ? WHERE id_task = ?";
    
    private static final String SQL_QUERY_SELECTALL = "SELECT id_task, id_form, id_step, id_question_list_value_closed, id_question_list_layer_carto, id_question_unit_tree FROM workflow_task_formscarto_config";
    private static final String SQL_QUERY_SELECT_BY_ID = SQL_QUERY_SELECTALL + " WHERE id_task = ?";


    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( FormsCartoTaskConfig formsCartoTaskConfig )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex , formsCartoTaskConfig.getIdTask( ) );
            if ( formsCartoTaskConfig.getForm( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getForm( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getStep( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getStep( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getQuestionListValueClosed( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getQuestionListValueClosed( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getQuestionListLayerCarto( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getQuestionListLayerCarto( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getQuestionUnitTree( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getQuestionUnitTree( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            
            daoUtil.executeUpdate( );
            
        }
        
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void store( FormsCartoTaskConfig formsCartoTaskConfig )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE ) )
        {
	        int nIndex = 0;
	        
	        if ( formsCartoTaskConfig.getForm( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getForm( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getStep( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getStep( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getQuestionListValueClosed( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getQuestionListValueClosed( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getQuestionListLayerCarto( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getQuestionListLayerCarto( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
            if ( formsCartoTaskConfig.getQuestionUnitTree( ) != null )
            {
                daoUtil.setInt( ++nIndex, formsCartoTaskConfig.getQuestionUnitTree( ).getId( ) );
            }
            else
            {
                daoUtil.setNull( ++nIndex, Types.INTEGER );
            }
	        daoUtil.setInt( ++nIndex , formsCartoTaskConfig.getIdTask( ) );
	
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public FormsCartoTaskConfig load( int nKey )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        FormsCartoTaskConfig formsCartoTaskConfig = null;
	
	        if ( daoUtil.next( ) )
	        {
	            formsCartoTaskConfig = loadFromDaoUtil( daoUtil );
	        }
	
	        return formsCartoTaskConfig;
        }
    }
        
     /**
     * Create a FormsCartoTaskConfig from daoUtil 
     * 
     * @param request
     *            request
     * @param locale
     *            locale
     * @param task
     *            the task
     * @return TaskTestConfig
     */
    private FormsCartoTaskConfig loadFromDaoUtil ( DAOUtil daoUtil ) 
    {
    
    	FormsCartoTaskConfig formsCartoTaskConfig = new FormsCartoTaskConfig( );
    	//int nIndex = 1;
    	
    	//formsCartoTaskConfig.setIdTask( daoUtil.getInt( nIndex++ ) );
    	
    	int nIndex = 0;

    	int idTask = daoUtil.getInt( ++nIndex );
        if ( idTask > 0 )
        {
        	formsCartoTaskConfig.setIdTask( idTask ) ;
        }
    	int idForm = daoUtil.getInt( ++nIndex );
        if ( idForm > 0 )
        {
        	formsCartoTaskConfig.setForm( FormHome.findByPrimaryKey( idForm ) );
        }
        int idStep = daoUtil.getInt( ++nIndex );
        if ( idStep > 0 )
        {
        	formsCartoTaskConfig.setStep( StepHome.findByPrimaryKey( idStep ) );
        }
        int idQuestionListValueClosed = daoUtil.getInt( ++nIndex );
        if ( idQuestionListValueClosed > 0 )
        {
        	formsCartoTaskConfig.setQuestionListValueClosed( QuestionHome.findByPrimaryKey( idQuestionListValueClosed ) );
        }
        int idQuestionListLaterCarto = daoUtil.getInt( ++nIndex );
        if ( idQuestionListLaterCarto > 0 )
        {
        	formsCartoTaskConfig.setQuestionListLayerCarto( QuestionHome.findByPrimaryKey( idQuestionListLaterCarto ) );
        }
        int idQuestionListUnitTree = daoUtil.getInt( ++nIndex );
        if ( idQuestionListUnitTree > 0 )
        {
        	formsCartoTaskConfig.setQuestionUnitTree( QuestionHome.findByPrimaryKey( idQuestionListUnitTree ) );
        }
        
        EditFormsCartoUnitTreeHome.getEditFormsCartoUnitTreesListByIdConfig( formsCartoTaskConfig.getIdTask() );
        formsCartoTaskConfig.setListEditFormsCartoUnitTree( EditFormsCartoUnitTreeHome.getEditFormsCartoUnitTreesListByIdConfig( formsCartoTaskConfig.getIdTask() ) );
    	
    	return formsCartoTaskConfig;
    }
    
    /**
    * {@inheritDoc }
    */
    @Override
    public void delete( int nKey )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeUpdate( );
        }
    }
}