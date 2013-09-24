jQuery(document).ready(function($) {        
    
    var wnTpl = {};
    
    wnTpl.isIE6 = ($.browser.msie && $.browser.version=="6.0") ? true : false;
    wnTpl.isIE7 = ($.browser.msie && $.browser.version=="7.0") ? true : false;
    wnTpl.isIE8 = ($.browser.msie && $.browser.version=="8.0") ? true : false; 
    wnTpl.isLtIE9 = (wnTpl.isIE6 || wnTpl.isIE7 || wnTpl.isIE8);



    /* ==========================================================================
     wnHideEmptySlogan
     ========================================================================== */
    wnTpl.wnHideEmptySlogan = function()
    {


        $("#moto").show();
        if ($("#rbcCompanySlogan").html() == "") {
            $("#moto").hide();
        }
        $(".illustrationSubpage #moto").hide();
    }   
    
/* ==========================================================================
   wnDetectItemsWithSubmenus
   - adds classname to each LI element with nested UL submenu
   ========================================================================== */
  
    wnTpl.wnDetectItemsWithSubmenus = function() 
    {                             
        $("#wrapper ul.menu li").each(function(index, object) 
        {           
            if( $(object).children("ul").length > 0) {
                $(object).addClass("withSubmenu"); 
            }
        });
    }
        
    
/* ==========================================================================
   wnAddClassToEmptyBreadcrumbs
   - adds specified classname when breadcrumbs are empty - we are on homepage
   ========================================================================== */
    wnTpl.wnAddClassToEmptyBreadcrumbs = function() 
    {
        if ($("#pageNavigator").html() == "") {
            $("#breadcrumbs").addClass("emptyBreadcrumbs");
        }
    }



/* ==========================================================================
   wnInitGalleryHovers
   - animated hovers on gallery images
   ========================================================================== */

    wnTpl.wnInitGalleryHovers = function()
    {

        if (!wnTpl.isIE6)
        {

            $("#wrapper .widgetPhotogallery .imgTitle").css("opacity","0").show();

            $('#wrapper .widgetPhotogallery .photoLink').hover(

                function()
                {
                    if (wnTpl.isLtIE9)
                    {
                        $(this).children("#wrapper .imgTitle").css("opacity","1");
                    }
                    else
                    {
                        $(this).children("#wrapper .imgTitle").stop().animate( { opacity: 1 }, 400 );
                    }
                },

                function()
                {
                    if (wnTpl.isLtIE9)
                    {
                        $(this).children("#wrapper .imgTitle").css("opacity","0");
                    }
                    else
                    {
                        $(this).children("#wrapper .imgTitle").stop().animate( { opacity: 0 }, 500 );
                    }
                }
            );

        }

    }
  
    
/* ==========================================================================
   _wnAddClassToNthChildren
   - private function used in wnFixNthChildInIE
   - adds specified classname to every "n"th element inside "ul" element
   ========================================================================== */    
    
    wnTpl._wnAddClassToNthChildren = function (ul, n, className) 
    {
              if (ul && n && className) {      
                    $(ul).children().each(function(index, object) 
                    {                       
                        if ( ((index+1) % n) == 0 ) 
                        {
                            $(object).addClass(className);
                        }                        
                    }); 
              }
    }           
    
    
/* ==========================================================================
   wnFixNthChildInIE
   - fix grid in old IEs
   ========================================================================== */
    wnTpl.wnFixNthChildInIE = function() 
    {               
        

        if (wnTpl.isLtIE9) 
        {          
  
              // remove class jsFixLastInRow from all photos
              $("#wrapper .widgetPhotogallery li.photo").removeClass("jsFixLastInRow");                
              
              
              // add class jsFixLastInRow to all last photos in rows in all galleries  
              
              // does the same as $("#wrapper .column100 .widgetPhotogallery118 li.photo:nth-child(6n + 0)").addClass("jsFixLastInRow");         
              $("#wrapper .column100 .widgetPhotogallery118 ul.photogallery").each(function(i, ul)
              {
                 wnTpl._wnAddClassToNthChildren(ul, 6, "jsFixLastInRow");
              }); 
              
              // does the same as $("#wrapper .column75  .widgetPhotogallery118 li.photo:nth-child(4n + 0)").addClass("jsFixLastInRow");         
              $("#wrapper .column75 .widgetPhotogallery118 ul.photogallery").each(function(i, ul)
              {
                 wnTpl._wnAddClassToNthChildren(ul, 4, "jsFixLastInRow");
              }); 
                         
              // does the same as $("#wrapper .column25  .widgetPhotogallery118 li.photo:nth-child(2n + 0)").addClass("jsFixLastInRow");    
              $("#wrapper .column25 .widgetPhotogallery118 ul.photogallery").each(function(i, ul)
              {
                 wnTpl._wnAddClassToNthChildren(ul, 2, "jsFixLastInRow");
              });               
              
  
                         
              // does the same as $("#wrapper .column100 .widgetPhotogallery200 li.photo:nth-child(4n + 0)").addClass("jsFixLastInRow");   
              $("#wrapper .column100 .widgetPhotogallery200 ul.photogallery").each(function(i, ul)
              {
                 wnTpl._wnAddClassToNthChildren(ul, 4, "jsFixLastInRow");
              });   
               
              // does the same as $("#wrapper .column75 .widgetPhotogallery200 li.photo:nth-child(3n + 0)").addClass("jsFixLastInRow");  
              $("#wrapper .column75 .widgetPhotogallery200 ul.photogallery").each(function(i, ul)
              {
                 wnTpl._wnAddClassToNthChildren(ul, 3, "jsFixLastInRow");
              });   
              
              
              // does the same as $("#wrapper .column33  .widgetPhotogallery118 li.photo:nth-child(2n + 0)").addClass("jsFixLastInRow");
              $("#wrapper .column33 .widgetPhotogallery118 ul.photogallery").each(function(i, ul)
              {
                 wnTpl._wnAddClassToNthChildren(ul, 2, "jsFixLastInRow");
              });     
              
       
         }         
        
    }   
   

   
    
/* ==========================================================================
   wnInitMainMenuHeader
   - fadeIn / fadeOut of administration block for editing main menu items
   ========================================================================== */
    wnTpl.wnInitMainMenuHeader = function() 
    {   

        if (!wnTpl.isIE6) 
        {      
              
              $("#mainMenu_header").css("opacity","0").show();
            	
              $('#mainMenu').hover(
                 
                     function() 
                     {
                          if (wnTpl.isLtIE9) 
                          {
                              $("#mainMenu_header").css("opacity","1");
                          }
                          else 
                          {
                              $("#mainMenu_header").stop().animate( { opacity: 1 }, 400 );
                          }                         
                     },
                     
                     function()
                     {
                          if (wnTpl.isLtIE9) 
                          {
                              $("#mainMenu_header").css("opacity","0");
                          }
                          else 
                          {                
                              $("#mainMenu_header").stop().animate( { opacity: 0 }, 500 );
                          }
                     }
              );
            
        } 
        
    }
    
    
    
/* ==========================================================================
   wnInitMainMenu
   - init submenus to be aligned correctly on mouse over
   ========================================================================== */
    wnTpl.wnInitMainMenu = function() 
    {              
   
        var fnFirstLevelOnHover = function () {   
           // Main Menu first level UL element
           var parentUl = $(this).parent();
           // get submenu UL element
           var subUl = $(this).children("ul:first");
                      
           if (subUl.length) {        
                 subUl.css("left", "0");
                 subUl.css("right", "auto");

                 if ( (subUl.offset().left + subUl.width()) > ($(window).width()) ) {                                         
                      subUl.css("left", "auto");
                      subUl.css("right", "0");
                 }                    
           }         
        }                     
    
        var fnSecondLevelOnHover = function () {       
          
           // main Menu second level UL element
           var parentUl = $(this).parent();
           // get 3.submenu UL element 
           var subUl = $(this).children("ul:first");  
             
           if (subUl.length) {    
                 subUl.show();                   
                 subUl.css("left", "205px");
                 subUl.css("right", "auto");              
                 
                 if ( (parentUl.width() + parentUl.offset().left + subUl.width()) > ($(window).width()) ) {               
                     subUl.css("left", "auto");
                     subUl.css("right", "205px");    
                 }   
           } 
                   
        }       
        
        var fnSecondLevelOnOut = function () {       
          
           // get 3.submenu UL element 
           var subUl = $(this).children("ul:first");  
   
           if (subUl.length) {    
              subUl.hide();                   
           } 
                   
        }           
    
        // set hover behavior on 1. level menu items
        $("#wrapper ul.menu > li").unbind('mouseenter mouseleave') 
        $("#wrapper ul.menu > li").hover(fnFirstLevelOnHover); 
        
        // set hover behavior on 2. level menu items
        $("#wrapper ul.menu > li > ul > li").unbind('mouseenter mouseleave');    
        $("#wrapper ul.menu > li > ul > li").hover(fnSecondLevelOnHover, fnSecondLevelOnOut);       
       
        // no need to take care about 3, 4, ... levels, 
        // all of these items are rendered into 3.level
       
  
    }       
    
    
    
/* ==========================================================================
   wnHideMainMenuOverflowedItems
   - hides main menu 1. level items which dont fit into the place for main menu
   ========================================================================== */
    wnTpl.wnHideMainMenuOverflowedItems = function() 
    {              
        wnTpl.menuItemsLengthCounter = 0;          
            
        $("#wrapper ul.menu").children().each(function(index, object) 
        {
            $(object).show();             
            wnTpl.menuItemsLengthCounter += $(object).outerWidth(true);                       
            if (wnTpl.menuItemsLengthCounter > $("#wrapper .menuWrapper").width()) {
               $(object).hide(); 
            }     
        });    
    }    
   
 
          
/* ==========================================================================
   wnInitSlideshow
   - init image slideshow when not using new image lightbox
   ========================================================================== */       
    wnTpl.wnInitSlideshow = function() 
    {   

         if (RubicusFrontendIns) 
         {
                         
               RubicusFrontendIns.addObserver(
               {                              
                  onStartSlideshow: function()
                  {                     
                     $('#slideshowControl').html('<span>' + wnTplSettings.photogallerySlideshowStop + '</span>');
                     $('#slideshowControl').attr('title', wnTplSettings.photogallerySlideshowStop);
                     $('#slideshowControl').attr('onClick', 'RubicusFrontendIns.stopSlideshow(); return(false);');                                           
                  },
             
                  onStopSlideshow: function()
                  {                   
                     $('#slideshowControl').html('<span>' + wnTplSettings.photogallerySlideshowStart + '</span>');
                     $('#slideshowControl').attr('title', wnTplSettings.photogallerySlideshowStart);
                     $('#slideshowControl').attr('onClick', 'RubicusFrontendIns.startSlideshow(); return(false);');                                             
                  },
             
                  onShowImage: function()
                  {
                     if (RubicusFrontendIns.isSlideshowMode())
                     {                                                 
                        $('#slideshowControl').html('<span>' + wnTplSettings.photogallerySlideshowStop + '</span>');
                        $('#slideshowControl').attr('title', wnTplSettings.photogallerySlideshowStop);
                        $('#slideshowControl').attr('onClick', 'RubicusFrontendIns.stopSlideshow(); return(false);');                     
                     }
                  }  
               });
         }
    }   
   
   
   
/* ==========================================================================
   wnInitPlaceholders
   - emulate placeholder functionality in older browsers
   ========================================================================== */       
    wnTpl.wnInitPlaceholders = function() 
    {   
         var test_input = document.createElement('input');
         var isSupportedPlaceholder = ('placeholder' in test_input);
         var inputs = document.getElementsByTagName('input');
         
         for (var i=0, l = inputs.length; i < l; i++)
         {
          if (false == isSupportedPlaceholder)
          {
           var placeholder = inputs[i].hasAttribute ? inputs[i].hasAttribute('placeholder') : !!inputs[i]['placeholder'];          
           if (placeholder)
           {                
            inputs[i].defaultValue = inputs[i].getAttribute ? inputs[i].getAttribute('placeholder') : inputs[i]['placeholder'];
            inputs[i].value = inputs[i].defaultValue;
            
            Event.observe(inputs[i], 'focus', function(event) {
             var el = event.target || event.srcElement;
             if (el.value == el.defaultValue)
             {
              el.value = '';
             }
            });
            
            Event.observe(inputs[i], 'blur', function(event) {
             var el = event.target || event.srcElement;
             if (el.value == '')
             {
              el.value = el.defaultValue;
             }
            });
           }
          }
         };        
    }    
   
   
 
   
/* ==========================================================================
   wnInitEvents
   - init handlers to onContentChange and onResize events
   ========================================================================== */
    wnTpl.wnInitEvents = function() 
    {   
         
         if (RubicusFrontendIns) 
         {         
             RubicusFrontendIns.addObserver
             ({          
                // onContentChange
                onContentChange: function()
                {                                                            
                   wnTpl.wnOnContentChangeHandler();
                },
                // onResize
                onResize: function()
                {                                             
                   wnTpl.wnOnContentChangeHandler();                                     
                }          
             });
         }           
    }  
   
          
/* ==========================================================================
   wnOnContentChangeHandler
   - handler to onContentChange Event
   ========================================================================== */
    wnTpl.wnOnContentChangeHandler = function() 
    {   
        wnTpl.wnDetectItemsWithSubmenus();
        wnTpl.wnInitGalleryHovers();
        wnTpl.wnFixNthChildInIE(); 
        wnTpl.wnInitMainMenu();
        wnTpl.wnHideMainMenuOverflowedItems(); 
        wnTpl.wnHideEmptySlogan();
    }            
          
          
/* ==========================================================================
   wnInit
   - init all template js functions
   ========================================================================== */
    wnTpl.wnInit = function() 
    {           
        /*wnTpl.wnOnContentChangeHandler();*/    
        // odstraniene bugu - zle sa vypocita sirka poloziek menu - 
        // pretoze este nie je nacitany google webfont
        // funkcia wnTpl.wnHideMainMenuOverflowedItems sa musi volat 
        // az na window.load   
        wnTpl.wnDetectItemsWithSubmenus();
        wnTpl.wnInitGalleryHovers();
        wnTpl.wnFixNthChildInIE(); 
        wnTpl.wnInitMainMenu(); 
        wnTpl.wnHideEmptySlogan();
        
        wnTpl.wnAddClassToEmptyBreadcrumbs();
        wnTpl.wnInitMainMenuHeader();       
        wnTpl.wnInitSlideshow();
        wnTpl.wnInitPlaceholders();                      
        wnTpl.wnInitEvents();     
        
        $(window).load(wnTpl.wnHideMainMenuOverflowedItems);
        
        // fix problému so zmenenou výškou ilustračného obrázku
        // na podstránkach, 100 znamená 100px - výšku obrázku na podstránke      
        if ( $("#illustration").hasClass("illustrationSubpage") )
        {
            if (Rubicus)
            {
                   Rubicus.headerEditor.gui.setCustomDefaultSize(null, 100);
            }               
        }

        // -------

    }            
                    
  
          
/* ==========================================================================
   calls
   - start point of all template scripts
   ========================================================================== */    
    wnTpl.wnInit();

});
  