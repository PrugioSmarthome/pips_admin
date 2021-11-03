package com.daewooenc.pips.admin.core.config;

import com.daewooenc.pips.admin.core.exception.MessageException;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.core.domain.common.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Exception Handler
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

	/** logger. */
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 *
	 * Error Handler 처리.
	 *
	 * @warning	[Optional]함수의 제약사항이나 주의해야 할 점
	 * @param e Exception
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ModelAndView
	 * @see	[Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ModelAndView handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();

		logger.error("Exception.class\n", e);

		ResponseInfo info = new ResponseInfo();

		if(e instanceof MessageException){
			String errorCode = ((MessageException)e).getErrorCode();

			try{
				info.setRetCode(errorCode);

				if(((MessageException)e).getArgs() == null);
				else{
					info.setRetMessage(MessageUtil.getMessage(errorCode, ((MessageException)e).getArgs()));
				}
			}catch(Exception ex){

			}
		}else{
			info.setRetCode("9999");
		}
		modelAndView.addObject("responseData", info);
		response.setStatus(500);

		if ((request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest"))) {
			MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

			modelAndView.setView(jsonView);
		}else{
			modelAndView.setViewName("exception/error_500");
		}

		return modelAndView;
	}


	/**
	 *
	 * HTTP 404... 처리
	 *
	 * @warning	[Optional]함수의 제약사항이나 주의해야 할 점
	 * @param request HttpServletRequest
	 * @param response  HttpServletResponse
	 * @param ex NoHandlerFoundException
	 * @return ModelAndView ModelAndView
	 * @see	[Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public ModelAndView requestHandlingNoHandlerFound(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException ex) {
		ModelAndView modelAndView = new ModelAndView();

		logger.error("NoHandlerFoundException.class\n", ex);

		ResponseInfo info = new ResponseInfo();
		info.setRetCode("9998");

		modelAndView.addObject("responseData", info);
		response.setStatus(404);

		if ((request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest"))) {
			MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

			modelAndView.setView(jsonView);
		}else{
			modelAndView.setViewName("exception/error_404");
		}

		return modelAndView;
	}
}
