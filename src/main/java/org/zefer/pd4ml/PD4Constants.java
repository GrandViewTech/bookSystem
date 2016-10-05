package org.zefer.pd4ml;

import java.awt.Dimension;

public class PD4Constants
{

    public PD4Constants()
    {
    }

    public static final Dimension A10 = new Dimension(74, 105);
    public static final Dimension A9 = new Dimension(105, 148);
    public static final Dimension A8 = new Dimension(148, 210);
    public static final Dimension A7 = new Dimension(210, 297);
    public static final Dimension A6 = new Dimension(297, 421);
    public static final Dimension A5 = new Dimension(421, 595);
    public static final Dimension A4 = new Dimension(595, 842);
    public static final Dimension A3 = new Dimension(842, 1190);
    public static final Dimension A2 = new Dimension(1190, 1684);
    public static final Dimension A1 = new Dimension(1684, 2384);
    public static final Dimension A0 = new Dimension(2384, 3370);
    public static final Dimension ISOB5 = new Dimension(501, 709);
    public static final Dimension ISOB4 = new Dimension(709, 1002);
    public static final Dimension ISOB3 = new Dimension(1002, 1418);
    public static final Dimension ISOB2 = new Dimension(1418, 2004);
    public static final Dimension ISOB1 = new Dimension(2004, 2836);
    public static final Dimension ISOB0 = new Dimension(2836, 4008);
    public static final Dimension HALFLETTER = new Dimension(396, 612);
    public static final Dimension LETTER = new Dimension(612, 792);
    public static final Dimension TABLOID = new Dimension(792, 1224);
    public static final Dimension LEDGER = new Dimension(1224, 792);
    public static final Dimension NOTE = new Dimension(540, 720);
    public static final Dimension LEGAL = new Dimension(612, 1008);
    public static final Dimension ArchA = new Dimension(648, 864);
    public static final Dimension ArchB = new Dimension(864, 1296);
    public static final Dimension ArchC = new Dimension(1296, 1728);
    public static final Dimension ArchD = new Dimension(1728, 2592);
    public static final Dimension ArchE = new Dimension(2592, 3456);
    public static final Dimension ArchE1 = new Dimension(2160, 3024);
    public static final double TRANSLATE_FACTOR = 2.835D;
    public static final int DefaultPermissions = -1;
    public static final int AllowDegradedPrint = 4;
    public static final int AllowModify = 8;
    public static final int AllowCopy = 16;
    public static final int AllowAnnotate = 32;
    public static final int AllowFillingForms = 256;
    public static final int AllowContentExtraction = 512;
    public static final int AllowAssembly = 1024;
    public static final int AllowPrint = 2052;
    public static final String PD4ML_IDS_AS_DESTINATIONS = "pd4ml.ids.as.destinations";
    public static final String PD4ML_MOBILE_DEVICE_WIDTH = "pd4ml.mobile.device.width";
    public static final String PD4ML_MOBILE_DEVICE_OPTIMIZE = "pd4ml.mobile.device.optimize";
    public static final String PD4ML_EXTRA_RESOURCE_LOADERS = "pd4ml.extra.resource.loaders";
    public static final String PD4ML_DOCUMENT_VIEW_MODE = "pd4ml.document.view.mode";
    public static final String PD4ML_DOCUMENT_DATE = "pd4ml.document.date";
    public static final String PD4ML_FAKE_BROWSER = "pd4ml.fake.browser";
    public static final String PD4ML_CACHE_ENABLE = "pd4ml.cache.enable";
    public static final String PD4ML_SESSIONID_APPEND = "pd4ml.sessionid.append";
    public static final String PD4ML_HTTP_PROXY = "pd4ml.http.proxy";
    public static final String PD4ML_PRINT_DIALOG_POPUP = "pd4ml.print.dialog.popup";
    public static final String PD4ML_MEDIA_TYPE_PRINT = "pd4ml.media.type.print";
    public static final String PD4ML_CSS_IMPORT_DIRECTIVE = "pd4ml.css.import.directive";
    public static final String PD4ML_BASIC_AUTHENTICATION = "pd4ml.basic.authentication";
    public static final String PD4ML_SESSIONID_VARNAME = "pd4ml.sessionid.varname";
    public static final String PD4ML_INITIAL_PAGE_NUMBER = "pd4ml.initial.page.number";
    public static final String PD4ML_ALLOWED_RESOURCE_LOCATION = "pd4ml.allowed.resource.location";
    public static final String PD4ML_DISABLE_EXTERNAL_ATTACHMENTS = "pd4ml.disable.external.attachments";
    public static final String PD4ML_GENERATE_TOOLTIPS = "pd4ml.generate.tooltips";
    public static final String PD4ML_ABSOLUTE_ADDRESS_SPACE = "pd4ml.absolute.address.space";
    public static final String PD4ML_CACHE_IMAGES_IN_TMP_DIR = "pd4ml.cache.images.in.tmp.dir";
    public static final String PD4ML_SUPPRESS_BLANK_PAGES = "pd4ml.suppress.blank.pages";
    public static final String PD4ML_HTTP_REQUEST_DISPATCHER = "pd4ml.http.request.dispatcher";
    public static final String PD4ML_PDFA_STATUS = "pd4ml.pdfa.status";
    public static final String PD4ML_DOCUMENT_HEIGHT_PX = "pd4ml.document.height.px";
    public static final String PD4ML_RIGHT_EDGE_PX = "pd4ml.right.edge.px";
    public static final String PD4ML_TOTAL_PAGES = "pd4ml.total.pages";
    public static final String PD4ML_INFO_AUTHOR = "pd4ml.info.author";
    public static final String PD4ML_INFO_TITLE = "pd4ml.info.title";
    public static final String PD4ML_LEGACY_RTL_MODE = "pd4ml.legacy.rtl.mode";
    public static final String PD4ML_FORCE_RTL = "pd4ml.force.rtl";
    public static final String PD4ML_BACKGROUND_AS_PATTERNS = "pd4ml.background.as.patterns";
    public static final String PD4ML_ENABLE_CANVAS_INTERPRETER = "pd4ml.enable.canvas.interpreter";
    public static final String PD4ML_CONFIG_FILE = "pd4ml.config.file";
    public static final String PD4ML_CONFIG_HANDLER_CLASS = "pd4ml.config.handler.class";
    public static final String PD4ML_IMAGE_REPOSITORY = "pd4ml.image.repository";
    public static final String PD4ML_FIX20130116 = "pd4ml.fix.20130116";
    public static final String PD4ML_FIX20150816 = "pd4ml.fix.20150816";
    public static final String PD4ML_STYLESHEETS_TO_OMIT = "pd4ml.stylesheets.to.omit";
    public static final String PD4ML_ENABLE_HTTP_ERRORS = "pd4ml.enable.http.errors";
    public static final String PD4ML_HEADER_HEIGHT_LIMIT = "pd4ml.header.height.limit";
    public static final String PD4ML_SOCKET_TIMEOUT = "pd4ml.socket.timeout";
    public static final String PD4ML_LEGACY_TEXT_BASELINE_COMPUTATION = "pd4ml.legacy.text.baseline.computation";
    public static final String PDF = "pdf";
    public static final String PDFA = "pdfa";
    public static final String RTF = "rtf";
    public static final String RTF_WMF = "rtfwmf";
    public static final String PNG8 = "png8";
    public static final String PNG24 = "png24";
    public static final String TIFF = "tiff";
    public static final String TIFF_COMPRESSED = "tiffcomp";
    public static final String PD4ML_EXTERNAL_CANVAS_HANDLER = "PD4CanvasHandler";

}
