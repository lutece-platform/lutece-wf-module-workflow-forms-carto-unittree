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

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.Step;
import fr.paris.lutece.plugins.workflow.modules.forms.business.EditFormResponseConfigValue;
import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

/**
 * Business class FormsCartoTaskConfig
 * Contains all configurations task.
 */ 
public class FormsCartoTaskConfig extends TaskConfig
{
    // Variables declaration
    private String _strFieldCarto;
    private int _nIdConfig = -1;
    private boolean _bMultiform = false;
    private List<EditFormResponseConfigValue> _listConfigValues = new ArrayList<>( );
    private List<EditFormsCartoUnitTree> _listEditFormsCartoUnitTree = new ArrayList<>();
    
    //private int _nIdConfigValue;
    //private int _nIdConfig;
    private Form _form;
    private Step _step;
    private Question _questionListValueClosed;
    private Question _questionListLayerCarto;
    private Question _questionUnitTree;
    //private String _strResponse;
    //private String _strCode;
    
    /**
     * Returns the FieldCarto
     * @return The FieldCarto
     */
    public String getFieldCarto( )
    {
        return _strFieldCarto;
    }

    /**
     * Sets the FieldCarto
     * @param strFieldCarto The FieldCarto
     */ 
    public void setFieldCarto( String strFieldCarto )
    {
        _strFieldCarto = strFieldCarto;
    }   
    
    /**
     * @return the nIdConfig
     */
    public int getIdConfig( )
    {
        return _nIdConfig;
    }

    /**
     * @param nIdConfig
     *            the nIdConfig to set
     */
    public void setIdConfig( int nIdConfig )
    {
        _nIdConfig = nIdConfig;
    }

    /**
     * @return the listConfigValues
     */
    public List<EditFormResponseConfigValue> getListConfigValues( )
    {
        return new ArrayList<>( _listConfigValues );
    }

    /**
     * @param listConfigValues
     *            the listConfigValues to set
     */
    public void setListConfigValues( List<EditFormResponseConfigValue> listConfigValues )
    {
        this._listConfigValues = new ArrayList<>( listConfigValues );
    }

    public void addConfigValue( EditFormResponseConfigValue configValue )
    {
        this._listConfigValues.add( configValue );
    }

    /**
     * @return the multiform
     */
    public boolean isMultiform( )
    {
        return _bMultiform;
    }

    /**
     * @param multiform
     *            the multiform to set
     */
    public void setMultiform( boolean multiform )
    {
        this._bMultiform = multiform;
    }

	public List<EditFormsCartoUnitTree> getListEditFormsCartoUnitTree() {
		return _listEditFormsCartoUnitTree;
	}

	public void setListEditFormsCartoUnitTree(List<EditFormsCartoUnitTree> _listEditFormsCartoUnitTree) {
		this._listEditFormsCartoUnitTree = _listEditFormsCartoUnitTree;
	}
	
	public void addEditFormsCartoUnitTree( EditFormsCartoUnitTree configValue )
    {
        this._listEditFormsCartoUnitTree.add( configValue );
    }
	
	/**
     * @return the step
     */
    public Step getStep( )
    {
        return _step;
    }

    /**
     * @param step
     *            the step to set
     */
    public void setStep( Step step )
    {
        _step = step;
    }

    /**
     * @return the question
     */
    public Question getQuestionListValueClosed( )
    {
        return _questionListValueClosed;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestionListValueClosed( Question question )
    {
        _questionListValueClosed = question;
    }
    
    /**
     * @return the question
     */
    public Question getQuestionListLayerCarto( )
    {
        return _questionListLayerCarto;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestionListLayerCarto( Question question )
    {
    	_questionListLayerCarto = question;
    }
    
    public Question getQuestionUnitTree() {
		return _questionUnitTree;
	}

	public void setQuestionUnitTree(Question _questionUnitTree) {
		this._questionUnitTree = _questionUnitTree;
	}

    public Form getForm( )
    {
        return _form;
    }

    public void setForm( Form form )
    {
        _form = form;
    }

	
}
