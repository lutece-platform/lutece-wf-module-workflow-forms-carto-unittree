package fr.paris.lutece.plugins.workflow.modules.formscarto.business;

import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.Step;

public class EditFormResponseConfigValueCarto
{
    private int _nIdConfigValue;
    private int _nIdConfig;
    private Form _form;
    private Step _step;
    private Question _question;
    private String _strResponse;
    private String _strCode;

    /**
     * @return the nIdConfigValue
     */
    public int getIdConfigValue( )
    {
        return _nIdConfigValue;
    }

    /**
     * @param nIdConfigValue
     *            the nIdConfigValue to set
     */
    public void setIdConfigValue( int nIdConfigValue )
    {
        _nIdConfigValue = nIdConfigValue;
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
    public Question getQuestion( )
    {
        return _question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion( Question question )
    {
        _question = question;
    }

    public Form getForm( )
    {
        return _form;
    }

    public void setForm( Form form )
    {
        _form = form;
    }

    /**
     * @return the response
     */
    public String getResponse( )
    {
        return _strResponse;
    }

    /**
     * @param strResponse
     *            the strResponse to set
     */
    public void setResponse( String strResponse )
    {
        _strResponse = strResponse;
    }

    /**
     * @return the strCode
     */
    public String getCode( )
    {
        return _strCode;
    }

    /**
     * @param strCode
     *            the strCode to set
     */
    public void setCode( String strCode )
    {
        _strCode = strCode;
    }
}
