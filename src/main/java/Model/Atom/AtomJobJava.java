
package Model.Atom;

import Model.DataModels.Atom.AtomInfo;
import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.EnumAggregationType;
import Model.DataModels.Datapoints.EnumResolutions;
import Model.PropertyChangeNames;
import Model.RestClient.OEResponse;
import View.Sites.EditSite.A_History.DatapointListTable.DatapointsListTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AtomJobJava {
    

    

    
  boolean stopProcessing = false;
  
  
  public void processChillerHistory( AtomInfo atomInfo , int xxxx ){
      
      //recommendationNode = getOrCreateRecommendationForSite(siteNode)
      //processChillerHistory(siteSid, siteNode.name, hours, dateTimeZone, commissionDate, recommendationNode.get)
      
  }
  
  

  

  /**
   * Gather history from chillers, pass to the MLR, and save results to the recommendation node.
   *
   * @param siteSid             The site to be processed
   * @param siteName            The name of the site to be processed
   * @param hours               Number of hours to "go back" for history
   * @param dateTimeZone        Time zone for this recommendation
   * @param commissionDate      An optional commission date
   * @param recommendationNode  Node where the recommendation results are saved
   */
  
  private void processChillerHistory(AtomInfo atomInfo ){
      
      DateTime end = Instant.now().minus(Duration.standardHours(1)).toDateTime();
      DateTime start = end.minus(Duration.standardHours(1)).toDateTime();
      
      //if (start.isBefore(atomInfo.commissionDate)) {
        //log.warning(s"Ignoring data prior to the commission date '${commissionDate.get.toString}'.")
        //start = atomInfo.commissionDate;
        
          //SiteHistoryBuilder siteHistoryBuilder = new SiteHistoryBuilder(atomInfo.timeZoneId);
          
          /* work in progress....
          for( AtomChillerInfo aci : atomInfo.chillersInfo ){
              
              siteHistoryBuilder.add( new SiteHistory(name, timeStamps, points));
          }
          
            AtomJobJava siteHistory;
            
            siteHistoryBuilder.getHistory( aci.chillerSid, aci.chillerName, aci.chillerCapacity, start, end, );
          
          //todo : get invalid combos
          
          siteHistoryBuilder.
          
          */
          
          
      }
  }
  
  
  
  
  
  /*
  def processChillerHistory(siteSid: Sid, siteName: String, hours: Int, dateTimeZone: DateTimeZone, commissionDate: Option[Instant], recommendationNode: DbNode): Unit = {
    log.info(s"Processing recommendations for site '$siteSid'.")

    val customerSid = siteSid.getCustomerSid

    val end = Instant.now().minus(Duration.standardHours(1)).toDateTime
    var start = end.minus(Duration.standardHours(hours)).toDateTime

    if (commissionDate.nonEmpty) {
      if (start.isBefore(commissionDate.get)) {
        log.warning(s"Ignoring data prior to the commission date '${commissionDate.get.toString}'.")
        start = commissionDate.get.toDateTime
      }
    }
    val siteHistoryBuilder = new SiteHistoryBuilder(dateTimeZone)

    val invalidChillerCombinations = recommendationNode.attrs.getOrElse(INVALID_CHILLER_COMBINATIONS, "").asInstanceOf[String]
    val invalidCombinations = invalidChillerCombinations.split("\n")

    try {
      log.debug(s"Searching graph hierarchy on site '$siteSid' for 'chiller'")
      ctx.client.graph.hierarchy(siteSid, Some("chiller"), None) match {
        case queryResponse: GraphQueryResponse =>
          log.debug("Received a chiller hierarchy response.")

          // Get chiller points
          var isValid = true
          queryResponse.nodes.foreach(chiller => {
            var chillerSid: Sid = null
            if (chiller.sid.isFullyQualified) {
              chillerSid = chiller.sid
            } else {
              chillerSid = siteSid.getCustomerSid.add(chiller.sid)
            }

            log.info(s"Getting points for chiller '$chillerSid'.")
            var capacity: Option[Double] = None
            if (chiller.properties.contains(TONNAGE)) {
              try {
                capacity = chiller.properties.get(TONNAGE).asInstanceOf[Some[Double]]
              } catch {
                case ex: Exception =>
                  log.warning(s"The chiller '$chillerSid' contains an attribute named '$TONNAGE' but it was not numeric.")
                  isValid = false
              }
            } else {
              log.warning(s"The chiller '$chillerSid' is missing a numeric attribute named '$TONNAGE' for this chiller's capacity.")
              isValid = false
            }

            // If we're not valid at this point then let's still try to fetch history because
            // if that also has problems then it would be nice see all errors at one time
            val siteHistory = getHistory(chillerSid, chiller.name, capacity, start, end, ATOM_CHILLER_POINTS)
            if (siteHistory != null) {
              log.debug(s"Adding history for chiller '$chillerSid'.")
              siteHistoryBuilder.add(siteHistory)
            } else {
              isValid = false
              log.warning(s"Unable to get points for chiller '$chillerSid'. Make sure the station points are aliased to the chiller.")
            }
          })

          // get station points
          val siteHistory = getHistory(siteSid, siteName, None, start, end, ATOM_STATION_POINTS)
          if (siteHistory != null) {
            log.info(s"Getting points for site '$siteSid'.")
            siteHistoryBuilder.add(siteHistory)
          } else {
            isValid = false
            log.warning(s"Unable to get points for site '$siteSid'. Make sure the station points are aliased to the site.")
          }

          // Get staging order
          if (isValid) {
            log.info(s"Generating recommendation table for site '$siteSid'.")
            try {
              val plantHistory = new PlantHistory(siteHistoryBuilder)
              val chillerStagingOrder = new ChillerStagingOrder(plantHistory.getChillerModels)
              val recommendationTable = getRecommendationTableAsString(chillerStagingOrder, isValidCombination = (row) => {
                !invalidCombinations.exists(p => row.endsWith(p))
              })

              save(customerSid, recommendationNode, recommendationTable)

              val printStats = chillerStagingOrder.makePrintStats(System.out, null, null)
              printStats.DumpFilePaths()
              printStats.DumpModels()
              printStats.DumpStagingOrder()

              // FIXME - the recommendation table in PrintStats *includes* the invalid combinations, which
              //         can be confusing. It's better to provide the invalid combinations to ChillerStagingOrder
              //         so that we have consistent results
              printStats.DumpTheRecommentationTable()

              log.info("=================================================")
              log.info("Recommendation Table without invalid combinations")
              log.info("=================================================")
              log.info(recommendationTable)

            } catch {
              case ex: Exception =>
                log.error(ex, s"Unable to get the staging order for site: $siteSid")
            }
          } else {
            log.warning("No data generated because of missing data point values.")
          }

        case ex =>
          log.error(s"Unable to find chillers for site: $siteSid. Message is: ${ex.msg}", ex)
      }
    } catch {
      case ex: Exception =>
        log.error(s"Unexpected error while trying to process a recommendation: ${ex.getMessage}", ex)
        log.error(s"${ex.toString}", ex)
    }

    log.info(s"Finished processing recommendations site '$siteSid'.")
  }
  
  */

  /**
   * Save recommendation table as a datapoint on the recommendation node.
   *
   * @param recommendationNode    Node to update
   * @param recommendationTable   Value to save as a datapoint
   */
  
  /*
  def save(customerSid: Sid, recommendationNode: DbNode, recommendationTable: String): Unit = {
    var recommendationSid: Sid = null
    if (recommendationNode.sid.isFullyQualified) {
      recommendationSid = recommendationNode.sid
    } else {
      recommendationSid = customerSid.add(recommendationNode.sid)
    }

    log.debug(s"Saving recommendation: '$recommendationSid")

    val dt = DateTime.now()
    val push = ctx.client.datapoints.historyPush(recommendationSid, dt)
    push.addJavaPoint(RECOMMENDATION_TABLE, dt, recommendationTable)

    val res = push.sendEach()
    if (res.success) {
      log.info(s"Successfully pushed the recommendation table to node '$recommendationSid'.")
    } else {
      log.error(s"Unable to push the recommendation table to node '$recommendationSid': ${res.msg}.")
    }

    log.debug("Finished saving recommendation.")
  }
  
  */

  /**
   * Get the recommendation table from the chiller staging order (MLR) but return it as a string.
   *
   * @param chillerStagingOrder   Chiller staging order (MLR)
   * @param isValidCombination    Function for determining if a particular stage (chiller combination) is valid
   * @return                      Recommendation table - without invalid combinations - as a String
   */
  
  /*
  def getRecommendationTableAsString(chillerStagingOrder: ChillerStagingOrder, isValidCombination: (String) => Boolean): String = {
    val rts = new StringBuilder(chillerStagingOrder.getHeaderRow)

    val rt = chillerStagingOrder.getRecommendationTable
    for (i <- 0 until rt.length) {
      val row = new StringBuilder()
      for (j <- 0 until rt(i).length) {
        if (j > 0) {
          row.append(",")
        }
        row.append(rt(i)(j))
      }
      if (isValidCombination(row.toString())) {
        if (rts.nonEmpty) {
          rts.append(System.lineSeparator())
        }
        rts.append(row.toString())
      }
    }
    rts.toString()
  }
  
  */

  /**
   * Find the recommendation node that is associate with a given site.
   * 
   * @param siteNode  The site node to search from.
   * @return          The associated recommendation node, if any.
   */
  
  /*
  def getOrCreateRecommendationForSite(siteNode: DbNode) : Option[DbNode] = {
    log.debug(s"Getting recommendation for site (sid): '${siteNode.sid}'")

    // We'll need a customer node for managing Sids, so grab that now
    val custNode = ctx.dbi.daoFor[GraphRepository].findCustomerById(siteNode.custId)

    var recommendationNode: Option[DbNode] = None

    // Each site has it's own recommendation
    val nodes = ctx.dbi.daoFor[GraphRepository].findHeadNodesByTailId(siteNode.id, G.RECOMMENDATION_ASSOCIATION_LABEL)
    nodes.foreach(n => {
      recommendationNode = Some(n)
    })

    if (recommendationNode.isEmpty) {
      // Let the recommendation sid be the same as the site sid
      val siteSidPart = siteNode.sid.find(SidTypes.Site).head
      val recommendationSid = custNode.sid.add(SidTypes.Recommendation, siteSidPart)

      val name = s"Recommendations for ${siteNode.sid}"

      val modifyResponse = ctx.client.graph.modifyGraph(custNode.sid)
        .addNode(recommendationSid, G.RECOMMENDATION_LABEL, name)
        .addEdge(siteNode.sid, recommendationSid, G.RECOMMENDATION_ASSOCIATION_LABEL, Some(Map[String, AnyRef](G.HIERARCHY_PROP -> java.lang.Boolean.TRUE)))
        .send()

      log.debug(s"Return value for adding recommendation node: ${modifyResponse.success}")

      if (modifyResponse.success) {
        val sid = recommendationSid.getCustomerSid.trim(recommendationSid)
        recommendationNode = Option(ctx.dbi.daoFor[GraphRepository].findNodeBySid(sid))
        log.info(s"Created a recommendation node: '$recommendationSid'")
      } else {
        log.error(s"Unable to create recommendation node: '${modifyResponse.msg}'")
      }
    }

    recommendationNode
  }

  def getHistory(sid: Sid, name: String, capacity: Option[Double], start: DateTime, end: DateTime, points: Set[String]): SiteHistory = {
    val dpv = mutable.Map[String, Array[_]]()

    val dpQueryResponse = ctx.client.datapoints.dataPointQuery(sid, start, end, points)
    if (dpQueryResponse != null && dpQueryResponse.success && dpQueryResponse.results.results.nonEmpty) {
      dpQueryResponse.results.results.foreach(col => {
        // FIXME - there's probably a better way to find/convert booleans to doubles
        if (col._1.equals("CHSBoolean")) {
          // convert to array of doubles
          val values = col._2.toArray
          val doubleValues = new Array[Double](values.length)
          for (i <- values.indices) {
            doubleValues(i) = if (values(i) == 1.0) 1.0 else 0.0
          }
          dpv += (col._1 -> doubleValues)
        } else {
          dpv += (col._1 -> col._2.toArray)
        }
      })

      val tb = TimeBucket.MINUTE5
      val timestamps = new Array[DateTime](tb.calculateArraySize(dpQueryResponse.results.start, dpQueryResponse.results.end))
      tb.calculateIntervals(start, tb.getNextInterval(end), new IntervalVisitor {
        override def visit(timestamp: Long, intervalNumber: Int): Unit =
          timestamps(intervalNumber) = new DateTime(timestamp, DateTimeZone.UTC)
      })

      if (capacity.isDefined) {
        new SiteHistory(name, capacity.get, timestamps, dpv.toMap.asInstanceOf[Map[String, Array[Double]]])
      } else {
        new SiteHistory(name, timestamps, dpv.toMap.asInstanceOf[Map[String, Array[Double]]])
      }

    } else {
      null
    }
  }

    */
  
  /* work in progres....
  public void getHistory( AtomChillerInfo aci ){

        List<DatapointsAndMetadataResponse> listOfPoints = new ArrayList<>(); //jListHistoryDatapoints.getSelectedValuesList();
        
        
        DatapointsAndMetadataResponse dpm = new DatapointsAndMetadataResponse();

        
        dpm.
        
        
        DatapointsListTableModel tableModel = (DatapointsListTableModel) (jTableDatapointsList.getModel());
        int[] selectedRowNumbers = jTableDatapointsList.getSelectedRows();
        for (int selectedRowNumber : selectedRowNumbers) {
            int modelRowNumber = jTableDatapointsList.convertRowIndexToModel(selectedRowNumber);
            DatapointsAndMetadataResponse dataRow = tableModel.getRow(modelRowNumber);
            listOfPoints.add(dataRow);
        }

        Map<String, List<String>> pointsToQuery = new HashMap<>();
        
        List<String> pointNames = new ArrayList<>();
        
        pointNames.add( aci.CHSBooleanName);
        
        pointsToQuery.put(aci.chillerSid, pointNames);
        
        if (pointsToQuery.size() > 0) {
            EnumResolutions res = EnumResolutions.MINUTE5;
            EnumAggregationType aggType = EnumAggregationType.NORMAL;
            if (jCheckBoxSum.isSelected()) {
                aggType = EnumAggregationType.SUM;
            } else if (jCheckBoxAvg.isSelected()) {
                aggType = EnumAggregationType.AVG;
            } else if (jCheckBoxCount.isSelected()) {
                aggType = EnumAggregationType.COUNT;
            }

            List<DatapointHistoriesQueryParams> listOfParams = new ArrayList<>();
            for (String sid : pointsToQuery.keySet()) {
                List<String> listOfPointNames = pointsToQuery.get(sid);
                DatapointHistoriesQueryParams params = new DatapointHistoriesQueryParams(
                        sid, historyQueryStart, historyQueryEnd, res, True, listOfPointNames, aggType);
                listOfParams.add(params);
            }



                List<DatapointHistoriesResponse> history = new ArrayList<>();
                for (DatapointHistoriesQueryParams params : listOfParams) {

                    OEResponse queryResult = client.getDatapointHistories(params);
                    if (queryResult.responseCode == 200) {
                        List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) queryResult.responseObject;
                        history.addAll(datapointHistoriesResponse);
                    } else {
                        Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                        logger.error(this.getClass().getName(), "History Query failed...");
                    }
                }
                OEResponse results = new OEResponse();
                results.responseCode = 200;
                results.responseObject = history;
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointHistoriesResponseReturned.getName(), null, datapointHistoriesResponse);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();

        }

  }

    */


