package edu.buaa.sei.datamodel;

public class Constants {

	public final static int MCOST_TYPE_AC[] = { 1, 10 };
	public final static int MCOST_TYPE_AP[] = { 1, 10 };
	public final static int MCOST_TYPE_RP[] = { 1, 10 };
	public final static int MCOST_TYPE_MA[] = { 1, 10 };
	public final static int MCOST_TYPE_MR[] = { 1, 10 };

	public final static String T_HEARTBEAT = "Heartbeat";
	public final static String T_PING_ECHO = "Ping/Echo";
	public final static String T_TIMESTAMP = "TimeStamp";
	public final static String T_SANITY_CHECKING = "SanityChecking";
	public final static String T_CONDITION_MONITORING = "ConditionMonitoring";
	public final static String T_EXCEPTION_DETECTION = "ExceptionDetection";
	public final static String T_SELF_TEST = "SelfTest";

	public final static String T_ACTIVE_REDUNDANCY = "ActiveRedundancy";
	public final static String T_PASSIVE_REDUNDANCY = "PassiveRedundancy";
	public final static String T_VOTING = "Voting";

	public final static String T_STATE_RESYNCHRONIZATION = "StateResynchronization";
	public final static String T_CHECKPOINT_ROLLBACK = "Checkpoint/Rollback";

	public final static String T_REMOVAL_FROM_SERVICE = "RemovalFromService";
	public final static String T_TRANSACTIONS = "Transactions";
	public final static String T_EXCEPTION_PREVENTION = "ExceptionPrevention";
	public final static String T_INCREASE_CONPETENCE_SET = "IncreaseConpetenceSet";

	public final static String T_EXCEPTION_HANDLING = "ExceptionHandling";
	public final static String T_SOFTWARE_UPGRADE = "SoftwareUpgrade";
	public final static String T_RETRY = "Retry";
	public final static String T_IGONRE_FAULTY_BEHAVIOR = "IgonreFaultyBehavior";
	public final static String T_DEGRADATION = "Degradation";
	public final static String T_RECONFIGURATION = "Reconfiguration";
	
	public final static int MAX_NUM_BACKUP = 3;

	public final static String FAULT_DETECTION_TACTIC[] = { T_HEARTBEAT,
			T_PING_ECHO, T_TIMESTAMP, T_SANITY_CHECKING,
			T_CONDITION_MONITORING, T_EXCEPTION_DETECTION, T_SELF_TEST };

	public final static String REDUNDANCY_TACTIC[] = { T_ACTIVE_REDUNDANCY,
			T_PASSIVE_REDUNDANCY, T_VOTING };

	public final static String FAULT_RECOVERY_TACTIC[] = {
			T_STATE_RESYNCHRONIZATION, T_CHECKPOINT_ROLLBACK };

	public final static String FAULT_PREVENTION_TACTIC[] = {
			T_REMOVAL_FROM_SERVICE, T_TRANSACTIONS, T_EXCEPTION_PREVENTION,
			T_INCREASE_CONPETENCE_SET };

	public final static String FAULT_REPAIR_TACTIC[] = { T_EXCEPTION_HANDLING,
			T_SOFTWARE_UPGRADE, T_RETRY, T_IGONRE_FAULTY_BEHAVIOR,
			T_DEGRADATION, T_RECONFIGURATION };

}
