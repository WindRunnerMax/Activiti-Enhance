package com.pressanykeytoac.form;

import org.activiti.engine.form.AbstractFormType;

public class voucherForm extends AbstractFormType
{

	/**
	 * author Czy
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "voucher";
	}

	@Override
	public Object convertFormValueToModelValue(String propertyValue) {
		// TODO Auto-generated method stub
		return propertyValue;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		// TODO Auto-generated method stub
		return (String) modelValue;
	}

}
