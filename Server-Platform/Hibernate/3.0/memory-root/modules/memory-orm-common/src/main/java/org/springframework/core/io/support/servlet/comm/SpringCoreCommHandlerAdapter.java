package org.springframework.core.io.support.servlet.comm;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.memory.platform.core.basic.BasicUHandler;

@Service("springCoreCommImpl")
/**
 * <strong>Spring's central class for synchronous client-side HTTP access.</strong>
 * It simplifies communication with HTTP servers, and enforces RESTful principles.
 * It handles HTTP connections, leaving application code to provide URLs
 * (with possible template variables) and extract results.
 *
 * <p>The main entry points of this template are the methods named after the six main HTTP methods:
 * <table>
 * <tr><th>HTTP method</th><th>RestTemplate methods</th></tr>
 * <tr><td>DELETE</td><td>{@link #delete}</td></tr>
 * <tr><td>GET</td><td>{@link #getForObject}</td></tr>
 * <tr><td></td><td>{@link #getForEntity}</td></tr>
 * <tr><td>HEAD</td><td>{@link #headForHeaders}</td></tr>
 * <tr><td>OPTIONS</td><td>{@link #optionsForAllow}</td></tr>
 * <tr><td>POST</td><td>{@link #postForLocation}</td></tr>
 * <tr><td></td><td>{@link #postForObject}</td></tr>
 * <tr><td>PUT</td><td>{@link #put}</td></tr>
 * <tr><td>any</td><td>{@link #exchange}</td></tr>
 * <tr><td></td><td>{@link #execute}</td></tr> </table>
 *
 * <p>The {@code exchange} and {@code execute} methods are generalized versions of the more specific methods listed
 * above them. They support additional, less frequently used combinations including support for requests using the
 * HTTP PATCH method. However, note that the underlying HTTP library must also support the desired combination.
 *
 * <p>For each of these HTTP methods, there are three corresponding Java methods in the {@code RestTemplate}.
 * Two variants take a {@code String} URI as first argument (eg. {@link #getForObject(String, Class, Object[])},
 * {@link #getForObject(String, Class, Map)}), and are capable of substituting any {@linkplain UriTemplate URI templates}
 * in that URL using either a {@code String} variable arguments array, or a {@code Map<String, String>}.
 * The string varargs variant expands the given template variables in order, so that
 * <pre class="code">
 * String result = restTemplate.getForObject("http://example.com/hotels/{hotel}/bookings/{booking}", String.class, "42",
 * "21");
 * </pre>
 * will perform a GET on {@code http://example.com/hotels/42/bookings/21}. The map variant expands the template based
 * on variable name, and is therefore more useful when using many variables, or when a single variable is used multiple
 * times. For example:
 * <pre class="code">
 * Map&lt;String, String&gt; vars = Collections.singletonMap("hotel", "42");
 * String result = restTemplate.getForObject("http://example.com/hotels/{hotel}/rooms/{hotel}", String.class, vars);
 * </pre>
 * will perform a GET on {@code http://example.com/hotels/42/rooms/42}. Alternatively, there are {@link URI} variant
 * methods ({@link #getForObject(URI, Class)}), which do not allow for URI templates, but allow you to reuse a single,
 * expanded URI multiple times.
 *
 * <p>Furthermore, the {@code String}-argument methods assume that the URL String is unencoded. This means that
 * <pre class="code">
 * restTemplate.getForObject("http://example.com/hotel list");
 * </pre>
 * will perform a GET on {@code http://example.com/hotel%20list}. As a result, any URL passed that is already encoded
 * will be encoded twice (i.e. {@code http://example.com/hotel%20list} will become {@code
 * http://example.com/hotel%2520list}). If this behavior is undesirable, use the {@code URI}-argument methods, which
 * will not perform any URL encoding.
 *
 * <p>Objects passed to and returned from these methods are converted to and from HTTP messages by
 * {@link HttpMessageConverter} instances. Converters for the main mime types are registered by default,
 * but you can also write your own converter and register it via the {@link #setMessageConverters messageConverters}
 * bean property.
 *
 * <p>This template uses a {@link org.springframework.http.client.SimpleClientHttpRequestFactory} and a
 * {@link DefaultResponseErrorHandler} as default strategies for creating HTTP connections or handling HTTP errors,
 * respectively. These defaults can be overridden through the {@link #setRequestFactory(ClientHttpRequestFactory)
 * requestFactory} and {@link #setErrorHandler(ResponseErrorHandler) errorHandler} bean properties.
 *
 * @author Arjen Poutsma
 * @author Brian Clozel
 * @since 3.0
 * @see HttpMessageConverter
 * @see RequestCallback
 * @see ResponseExtractor
 * @see ResponseErrorHandler
 * @see AsyncRestTemplate
 */
public class SpringCoreCommHandlerAdapter implements InitializingBean{
	private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	/**
	 * Create a new instance of the {@link RestTemplate} based on the given {@link ClientHttpRequestFactory}.
	 * 
	 * @param requestFactory
	 *            HTTP request factory to use
	 * @see org.springframework.http.client.SimpleClientHttpRequestFactory
	 * @see org.springframework.http.client.HttpComponentsClientHttpRequestFactory
	 */
	public SpringCoreCommHandlerAdapter() {

	}

	/**
	 * Create a new instance of the {@link RestTemplate} using the given list of {@link HttpMessageConverter} to use
	 * 
	 * @param messageConverters
	 *            the list of {@link HttpMessageConverter} to use
	 * @since 3.2.7
	 */
	public SpringCoreCommHandlerAdapter(List<HttpMessageConverter<?>> messageConverters) {
		Assert.notEmpty(messageConverters, "'messageConverters' must not be empty");
		this.messageConverters.addAll(messageConverters);
	}

	/**
	 * Set the message body converters to use.
	 * <p>
	 * These converters are used to convert from and to HTTP requests and responses.
	 */
	public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
		Assert.notEmpty(messageConverters, "'messageConverters' must not be empty");
		this.messageConverters.clear();
		this.messageConverters.addAll(messageConverters);
	}

	/**
	 * Return the message body converters.
	 */
	public List<HttpMessageConverter<?>> getMessageConverters() {
		return this.messageConverters;
	}

	/**
	 * <p>
	 * Objects passed to and returned from these methods are converted to and from HTTP messages by
	 * {@link HttpMessageConverter} instances. Converters for the main mime types are registered by default, but you can
	 * also write your own converter and register it via the {@link #setMessageConverters messageConverters} bean
	 * property.
	 * 
	 * <p>
	 * This template uses a {@link org.springframework.http.client.SimpleClientHttpRequestFactory} and a
	 * {@link DefaultResponseErrorHandler} as default strategies for creating HTTP connections or handling HTTP errors,
	 * respectively. These defaults can be overridden through the {@link #setRequestFactory(ClientHttpRequestFactory)
	 * requestFactory} and {@link #setErrorHandler(ResponseErrorHandler) errorHandler} bean properties.
	 * @throws ServerException 
	 * @throws Exception 
	 */
	@PostConstruct
	private void basicFun() {
		BasicUHandler.handlerAdapter();
	}

	/**
	 * Set the error handler.
	 * <p>
	 * By default, RestTemplate uses a {@link DefaultResponseErrorHandler}.
	 */
	public void setErrorHandler(ResponseErrorHandler errorHandler) {
		Assert.notNull(errorHandler, "'errorHandler' must not be null");
		this.errorHandler = errorHandler;
	}

	/**
	 * Return the error handler.
	 */
	public ResponseErrorHandler getErrorHandler() {
		return this.errorHandler;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		BasicUHandler.handlerAdapter();
	}

}
