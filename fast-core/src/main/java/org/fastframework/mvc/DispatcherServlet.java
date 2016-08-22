package org.fastframework.mvc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fastframework.mvc.method.HandlerMethod;
import org.fastframework.mvc.method.RequestMappingHandlerAdapter;
import org.fastframework.mvc.util.MVCHelper;
import org.fastframework.mvc.view.InternalResourceViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MVC 前端控制器 1. 初始化相关配置: 类扫描/路由匹配 2. 转发请求到 HandlerMapping 3.
 * 反射调用Controller方法,并解耦 4. 根据返回值,响应视图或数据
 *
 * Created by bysocket on 16/7/19.
 */
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

	private List<HandlerAdapter> handlerAdapters;

	private List<ViewResolver> viewResolvers;

	/**
	 * 初始化相关配置 扫描类 - 路由Map
	 *
	 * @throws ServletException
	 */
	@Override
	public void init() throws ServletException {
		ControllerCollection.init();
		initHandlerAdapters();
		initViewResolvers();
	}

	protected void initHandlerAdapters() {
		if (handlerAdapters == null) {
			handlerAdapters = new LinkedList<HandlerAdapter>();
			RequestMappingHandlerAdapter methodAdapter = new RequestMappingHandlerAdapter();
			handlerAdapters.add(methodAdapter);
		}
	}

	protected void initViewResolvers() {
		if (viewResolvers == null) {
			viewResolvers = new LinkedList<ViewResolver>();
			InternalResourceViewResolver urlView = new InternalResourceViewResolver();
			viewResolvers.add(urlView);
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求默认编码
		request.setCharacterEncoding(MVCHelper.REQ_CHARACTER_UTF_8);
		// 请求相关信息
		// 请求方法 [POST] [GET]
		String requestMethod = request.getMethod();
		// 请求路由
		String requestPath = MVCHelper.getRequestPath(request);

		LOGGER.debug("[fast framework] {} : {}", requestMethod, requestPath);

		// "/" 请求重定向到默认首页
		if (MVCHelper.URL_PATH_SEPARATOR.equals(requestPath)) {
			MVCHelper.redirectRequest(MVCHelper.REQ_DEFAULT_HOME_PAGE, request, response);
			return;
		}

		// 处理器映射
		// 获取 handler
		HandlerMethod handler = HandlerMapping.getHandler(requestMethod, requestPath);
		// null == handler
		if (null == handler) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// 获取 ModelAndView
		HandlerAdapter ha = getHandlerAdapter(handler);
		ModelAndView mv;
		try {
			mv = ha.handle(request, response, handler);
			if (mv == null) {
				return;
			}
			render(mv, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	protected HandlerAdapter getHandlerAdapter(HandlerMethod handler) throws ServletException {
		for (HandlerAdapter ha : this.handlerAdapters) {
			if (ha.supports(handler)) {
				return ha;
			}
		}
		throw new ServletException("No adapter for handler [" + handler
				+ "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
	}

	protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		View view;
		// View 处理
		if (mv.isReference()) {
			// viewName 处理
			view = resolveViewName(mv.getViewName(), mv.getModelInternal());
		} else {
			// ModelAndView 中包含一个 View
			view = mv.getView();
		}
		try {
			view.render(mv.getModelInternal(), request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected View resolveViewName(String viewName, Map<String, Object> model) throws Exception {
		for(ViewResolver viewResolver:viewResolvers){
			View view = viewResolver.resolveViewName(viewName);
			if(view != null){
				LOGGER.debug("[fast framework] found view :{}",view.getClass().getName());
				return view;
			}
		}
		return null;
	}
}
